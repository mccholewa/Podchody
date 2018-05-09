package com.podchody.ui.newgameplayer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.podchody.NavigationController
import com.podchody.databinding.NewgameFragmentBinding
import com.podchody.ui.lobby.LobbyViewModel
import com.podchody.util.OnBackPressedListener
import com.podchody.util.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by Misiu on 19.04.2018.
 */
class NewgameplayerFragment: Fragment(), OnBackPressedListener {


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

    lateinit var startEventListener: ValueEventListener
    lateinit var startRef: DatabaseReference
    lateinit var roleRef: DatabaseReference
    lateinit var roleEventListener: ValueEventListener

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
        startRef = db.reference.child("games").child(arguments!!.getString("key")).child("started")
        roleRef = db.reference.child("games").child(arguments!!.getString("key")).child("player").child("role")
        startEventListener = createStartEventListener()
        roleEventListener = createRoleEventListener()
        binding.tvHost.text = arguments!!.getString("hostName")
        binding.rbHostSeeker.isClickable = false
        binding.rbPlayerRunner.isClickable = false
        binding.rbPlayerSeeker.isClickable = false
        binding.rbHostRunner.isClickable = false
        binding.start.visibility = INVISIBLE
        binding.tvGameTitleFragment.text = arguments!!.getString("title")
        binding.tvPlayer.text = auth.currentUser!!.displayName

        //  binding.viewModel = viewModel
//        viewModel.liveData.observe(this) {
//            //     binding.state = it
//        }
//        viewModel.uiActions.observe(this) { it(activity!!) }

    }

    fun createRoleEventListener(): ValueEventListener {
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                if (dataSnapshot != null)
                    if (dataSnapshot.value == "seeker") {
                        binding.rbHostSeeker.isChecked = false
                        binding.rbHostRunner.isChecked = true
                        binding.rbPlayerRunner.isChecked = false
                        binding.rbPlayerSeeker.isChecked = true
                    } else {
                        binding.rbHostSeeker.isChecked = true
                        binding.rbHostRunner.isChecked = false
                        binding.rbPlayerRunner.isChecked = true
                        binding.rbPlayerSeeker.isChecked = false
                    }
            }

            override fun onCancelled(e: DatabaseError?) {
            }
        }
        return listener
    }


    fun createStartEventListener(): ValueEventListener {
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                if (dataSnapshot != null) {
                    if (dataSnapshot.value == true) {
                        if (binding.rbPlayerRunner.isChecked == true)
                            navigationController.navigateToMapFragment(activity!!, arguments!!.getString("key"))
                        if (binding.rbPlayerRunner.isChecked == false)
                            navigationController.navigateToMapplayerFragment(activity!!, arguments!!.getString("key"))
                    }
                } else {
                    db.reference.child("users").child(auth.currentUser!!.uid).child("game").removeValue()
                    navigationController.navigateToLobby(activity!!)
                }
            }

            override fun onCancelled(e: DatabaseError?) {
            }
        }
        return listener
    }

    override fun onBackPressed(): Boolean {
        db.reference.child("users").child(auth.currentUser!!.uid).child("game").removeValue()
        db.reference.child("games").child(arguments!!.getString("key")).child("player").child("name").removeValue()
        db.reference.child("games").child(arguments!!.getString("key")).child("player").child("uid").removeValue()
        navigationController.navigateToLobby(activity!!)
        return true
    }

    override fun onStart() {
        super.onStart()
        startRef.addValueEventListener(startEventListener)
        roleRef.addValueEventListener(roleEventListener)
    }

    override fun onStop() {
        super.onStop()
        startRef.removeEventListener(startEventListener)
        roleRef.removeEventListener(roleEventListener)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}