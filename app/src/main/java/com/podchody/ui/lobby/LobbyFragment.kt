package com.podchody.ui.lobby

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.podchody.NavigationController
import com.podchody.databinding.LobbyFragmentBinding
import com.podchody.modle.GameFb
import com.podchody.modle.UserFb
import com.podchody.util.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider
import android.content.DialogInterface
import android.text.Editable
import android.widget.EditText
import android.R.id
import android.support.design.widget.Snackbar
import com.podchody.R
import com.podchody.R.layout.*
import com.podchody.modle.PlayerFb
import com.podchody.vo.StringLanguageResource
import kotlinx.android.synthetic.main.newgame_dialog.view.*
import java.util.*
import kotlin.math.acos


/**
 * Created by Misiu on 03.04.2018.
 */

class LobbyFragment: Fragment() {

    @Inject
    lateinit var viewModelProvider: Provider<LobbyViewModel>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var navigationController: NavigationController

    @Inject
    lateinit var db: FirebaseDatabase

    @Inject
    lateinit var auth: FirebaseAuth


    private var adapter: FirebaseRecyclerAdapter<GameFb, LobbyViewHolder>? = null
    private var userFbConnectedListener: ChildEventListener? = null
    private var userFbList = mutableListOf<UserFb>()
    private var userFbReference: DatabaseReference? = null
    private var currentUserReference: DatabaseReference? = null

    private val viewModel by lazy {
        viewModelFactory(this, viewModelProvider)
    }

    lateinit var binding: LobbyFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = LobbyFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

      override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop(){
        super.onStop()
        adapter!!.stopListening()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        currentUserReference = db.reference.child("users").child(auth.currentUser!!.uid).child("connected")
//        currentUserReference!!.onDisconnect().setValue(false)

        //rv_onlineuser_list.itemAnimator = DefaultItemAnimator()
        userFbList = mutableListOf()
        adapter = createGameAdapter()
        binding.rvList.layoutManager = LinearLayoutManager(activity)
        binding.rvList.adapter = adapter
        binding.btnAdd.setOnClickListener{ btnCreatNewgame() }

//        val onlineUsers = db.reference.child("users").orderByChild("connected").equalTo(true)
//        onlineUsers.addChildEventListener(userFbConnectedListener)


        viewModel.liveData.observe(this){
            binding.state = it
        }

        viewModel.uiActions.observe(this){it(activity!!)}

        binding.viewModel = viewModel

    }

    fun reloadGame(){
        val user = db.reference.child("games").orderByKey().equalTo(auth.currentUser!!.uid)
        user.addListenerForSingleValueEvent( object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                for(i in 0..10){
                    Timber.d("                                                                                          player in game  %s", dataSnapshot!!.child("name").value.toString())
                }
            }
            override fun onCancelled(e: DatabaseError?) {
                if (e != null)
                    navigationController.showError(activity!!, e.toException().toString())
            }
        })
    }

    fun btnCreatNewgame(){
        val language = Locale.getDefault().displayLanguage
        val dialogBuilder = AlertDialog.Builder(activity!!)
        val dialogView = this.layoutInflater.inflate(newgame_dialog, null)
        dialogBuilder.setView(dialogView)
        val alertDialog = dialogBuilder.create()
        dialogView.btn_create_game.setOnClickListener{
            val title = dialogView.et_game_title.text.toString()
            if(title.isBlank()) {
                navigationController.showError(activity!!, StringLanguageResource(language).noGameTitle)
                Snackbar.make(dialogView, StringLanguageResource(language).noGameTitle, Snackbar.LENGTH_LONG).show()
                Timber.d("                                          create new game error ")
            }
            else{
                Timber.d("                                                          title %s", title)
                val gameRef = db.reference.child("games").push()
                val host = PlayerFb(auth.currentUser!!.uid,  "runner")
                db.reference.child("games").child(gameRef.key).child("host").setValue(host)
                db.reference.child("games").child(gameRef.key).child("title").setValue(title)
                db.reference.child("games").child(gameRef.key).child("started").setValue(false)
                db.reference.child("users").child(auth.currentUser!!.uid).child("game").setValue(gameRef.key)
                navigationController.navigateToNewgame(activity!!, title,gameRef.key)
                alertDialog.cancel()
            }
        }
        alertDialog.show()

        dialogView.btn_exit.setOnClickListener{
            alertDialog.cancel()
        }
    }


    fun createGameAdapter():FirebaseRecyclerAdapter<GameFb, LobbyViewHolder> {
        Timber.d("                                                              Adapter full")
        val query = db.reference.child("games").orderByChild("player").equalTo(null)
        val options = FirebaseRecyclerOptions.Builder<GameFb>()
                .setQuery(query, GameFb::class.java)
                .build()
        val adapter = object : FirebaseRecyclerAdapter<GameFb, LobbyViewHolder>(options) {
            override fun onBindViewHolder(holder: LobbyViewHolder, position: Int, model: GameFb) {
                getRef(position).key
                holder.name.text = model.title
                Timber.d("                                                              User item id: %s", model.title)
                Timber.d("                                                              User connected: %s", model.started)
                holder.name.setOnClickListener { navigationController.navigateToNewgame(activity!!, model.title!!,getRef(position).key) }
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LobbyViewHolder {
                val view = LayoutInflater.from(parent.context)
                        .inflate(game_item, parent, false)
                return LobbyViewHolder(view)
            }

            override fun onError(e: DatabaseError) {
                super.onError(e)
                Timber.d("                                                              Adapter error: %s", e.toException().toString())
                navigationController.showError(activity!!, e.message)
            }
        }
        adapter.notifyDataSetChanged()
        return adapter
    }

    fun createUserOnlineAdapter():FirebaseRecyclerAdapter<UserFb, OnlineUserViewHolder>{
        Timber.d("                                                              Adapter full")
        val onlineUsers = db.reference.child("users").orderByChild("connected").equalTo(true).orderByChild("ingame").equalTo(false)
        val options = FirebaseRecyclerOptions.Builder<UserFb>()
                .setQuery(onlineUsers , UserFb::class.java)
                .build()
        val adapter = object : FirebaseRecyclerAdapter<UserFb, OnlineUserViewHolder>(options) {
            override fun onBindViewHolder(holder: OnlineUserViewHolder, position: Int, model: UserFb) {
                getRef(position).key
                holder.name.text = model.name
                Timber.d("                                                              User item id: %s", model.email)
                Timber.d("                                                              User connected: %s", model.connected.toString())
                holder.name.setOnClickListener { }
            }
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnlineUserViewHolder {
                val view = LayoutInflater.from(parent.context)
                        .inflate(user_item, parent, false)
                return OnlineUserViewHolder(view)
            }
            override fun onError(e: DatabaseError) {
                super.onError(e)
                Timber.d("                                                              Adapter error: %s", e.toException().toString())
                navigationController.showError(activity!!, e.message)
            }
        }
        adapter.notifyDataSetChanged()
        return adapter
    }


//        userFbConnectedListener = object : ChildEventListener{
//            override fun onChildChanged(dataSnapshot: DataSnapshot?, childName: String?) {
//                if (dataSnapshot != null) {
//                    val uid = dataSnapshot.key
//                    for (item in dataSnapshot.children) {
//                        Timber.d("                                      User user id: %s", dataSnapshot.key)
//                        Timber.d("                                      User item id: %s", item.key)
//                        Timber.d("                                      User connected: %s", item.value.toString())
////                        val user: UserFb()
////                        userFbList.add()
//                    }
//                }
//                adapter!!.notifyDataSetChanged()
//                rv_onlineuser_list.adapter = adapter
//            }
//            override fun onChildAdded(dataSnapshot: DataSnapshot?, childName: String?) {
//                if (dataSnapshot != null) {
//                    val uid = dataSnapshot.key
//                    for (item in dataSnapshot.children) {
//                        Timber.d("                                      User user id: %s", dataSnapshot.key)
//                        Timber.d("                                      User item id: %s", item.key)
//                        Timber.d("                                      User connected: %s", item.value.toString())
////                        val user: UserFb()
////                        userFbList.add()
//                    }
//                }
//            }
//            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//            override fun onChildRemoved(p0: DataSnapshot?) {
//            }
//
//             override fun onCancelled(databaseError: DatabaseError?) {
//                 Timber.d("postMessages:onCancelled:    %s", databaseError!!.toException().toString())
//             }
//        }



}