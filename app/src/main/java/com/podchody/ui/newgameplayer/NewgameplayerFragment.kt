package com.podchody.ui.newgameplayer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.podchody.NavigationController
import com.podchody.databinding.NewgameFragmentBinding
import com.podchody.ui.lobby.LobbyViewModel
import com.podchody.util.ViewModelFactory
import com.podchody.vo.StringLanguageResource
import dagger.android.support.AndroidSupportInjection
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by Misiu on 19.04.2018.
 */
class NewgameplayerFragment: Fragment() {


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

    lateinit var playerEventListener: ValueEventListener
    lateinit var hostRef: DatabaseReference

    private var playerName:String? = ""

    lateinit var playerUid: String
    lateinit var playerNameRef:DatabaseReference

    private val viewModel by lazy {
        viewModelFactory(this, viewModelProvider)
    }

    lateinit var binding: NewgameFragmentBinding

    lateinit var time: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = NewgameFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        hostRef = db.reference.child("games").child(arguments!!.getString("key")).child("host").child("uid")
        playerEventListener = createPlayerEventListener()
        val gameRef = db.reference.child("games").child(arguments!!.getString("key"))
        gameRef.onDisconnect().removeValue()
        binding.rbHostSeeker.isClickable = false
        binding.rbPlayerRunner.isClickable = false
        binding.rbPlayerSeeker.isClickable = false
        binding.rbHostSeeker.isClickable = false


        Timber.d("                                                                                      title %s",arguments!!.getString("title"))
        binding.tvGameTitleFragment.text = arguments!!.getString("title")
        binding.tvHost.text = auth.currentUser!!.displayName
        binding.start.setOnClickListener{
            //navigationController.navigateToMapFragment(activity!!)

        }






        //  binding.viewModel = viewModel
//        viewModel.liveData.observe(this) {
//            //     binding.state = it
//        }
//        viewModel.uiActions.observe(this) { it(activity!!) }

    }

        fun createPlayerEventListener(): ValueEventListener{
            val language = Locale.getDefault().displayLanguage
            val listener = object : ValueEventListener{

                override fun onDataChange(dataSnapshot: DataSnapshot?) {
                        playerNameRef = db.reference.child("users").child(dataSnapshot!!.value.toString()).child("name")
                        Timber.d("                                                                                               player uid %s",dataSnapshot.child("uid").value)
                        playerNameRef.addListenerForSingleValueEvent( object : ValueEventListener{

                            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                                //val player: String?= dataSnapshot!!.child("name").value.toString()
                                if (dataSnapshot!!.exists())
                                    binding.tvPlayer.text = dataSnapshot.value.toString()
                                else {

                                    Timber.d("                                                                                          player name  %s", dataSnapshot.child("name").value.toString())
                                }
                            }
                            override fun onCancelled(e: DatabaseError?) {
                                if (e != null)
                                    navigationController.showError(activity!!, e.toException().toString())
                            }
                        })
                }
                override fun onCancelled(e: DatabaseError?) {
                    navigationController.showError(activity!!, e!!.toException().toString())
                }
            }
            return listener
        }

    override fun onStart() {
        super.onStart()
        hostRef.addValueEventListener(playerEventListener)
    }

    override fun onStop() {
        super.onStop()
        hostRef.removeEventListener(playerEventListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        db.reference.child("games").child(arguments!!.getString("key")).removeValue()
    }

}