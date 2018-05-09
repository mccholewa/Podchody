package com.podchody.ui.newgame

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
import com.podchody.util.OnBackPressedListener
import java.io.IOException


/**
 * Created by Misiu on 19.04.2018.
 */
class NewgameFragment: Fragment(),OnBackPressedListener {

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
    lateinit var playerRef: DatabaseReference

    private var playerName: String? = null
    private val language = Locale.getDefault().displayLanguage
    lateinit var playerNameRef: DatabaseReference
    lateinit var gameRef: DatabaseReference

    private val viewModel by lazy {
        viewModelFactory(this, viewModelProvider)
    }

    lateinit var binding: NewgameFragmentBinding

    lateinit var time: String

    override fun onBackPressed(): Boolean {
        db.reference.child("games").child(arguments!!.getString("key")).removeValue()
        db.reference.child("users").child(auth.currentUser!!.uid).child("game").removeValue()
        navigationController.navigateToLobby(activity!!)
        return true
    }

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

        playerRef = db.reference.child("games").child(arguments!!.getString("key")).child("player").child("name")
        gameRef = db.reference.child("games").child(arguments!!.getString("key"))
        playerEventListener = createPlayerEventListener()
        binding.rbHostRunner.isChecked = true
        binding.rbPlayerSeeker.isChecked = true
        db.reference.child("games").child(arguments!!.getString("key")).child("host").child("role").setValue("runner")
        db.reference.child("games").child(arguments!!.getString("key")).child("player").child("role").setValue("seeker")


        binding.tvGameTitleFragment.text = arguments!!.getString("title")
        binding.tvHost.text = auth.currentUser!!.displayName
        binding.start.setOnClickListener {
            if(isOnline()){
                if (this.playerName != null) {
                    db.reference.child("games").child(arguments!!.getString("key")).child("started").setValue(true)
                    if (binding.rbHostRunner.isChecked == true)
                        navigationController.navigateToMapFragment(activity!!, arguments!!.getString("key"))
                    else navigationController.navigateToMapplayerFragment(activity!!, arguments!!.getString("key"))
                } else
                    navigationController.showError(activity!!, StringLanguageResource(language).needSecondPlayer)
            } else  navigationController.showError(activity!!, StringLanguageResource(language).noNetwork)
        }
        setRadioButtonsListener()


        //  binding.viewModel = viewModel
        viewModel.liveData.observe(this) {
            //     binding.state = it
        }
        viewModel.uiActions.observe(this) { it(activity!!) }

    }

    fun createPlayerEventListener(): ValueEventListener {

        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                if (dataSnapshot!!.exists()) {
                    db.reference.child("games").child(arguments!!.getString("key")).child("full").setValue(true)
                    playerName = dataSnapshot.value.toString()
                    binding.tvPlayer.text = playerName
                } else {
                    playerName = null
                    binding.tvPlayer.text = StringLanguageResource(language).waitingForPlayer
                    db.reference.child("games").child(arguments!!.getString("key")).child("full").setValue(false)
                }
            }

            override fun onCancelled(e: DatabaseError?) {}
        }
        return listener
    }

    fun setRadioButtonsListener() {
        val hostRole = db.reference.child("games").child(arguments!!.getString("key")).child("host").child("role")
        val playerRole = db.reference.child("games").child(arguments!!.getString("key")).child("player").child("role")
        binding.rbHostRunner.setOnClickListener {
            binding.rbHostSeeker.isChecked = false
            binding.rbPlayerRunner.isChecked = false
            binding.rbPlayerSeeker.isChecked = true
            hostRole.setValue("runner")
            playerRole.setValue("seeker")

        }
        binding.rbHostSeeker.setOnClickListener {
            binding.rbHostRunner.isChecked = false
            binding.rbPlayerRunner.isChecked = true
            binding.rbPlayerSeeker.isChecked = false
            hostRole.setValue("seeker")
            playerRole.setValue("runner")
        }

        binding.rbPlayerRunner.setOnClickListener {
            binding.rbHostSeeker.isChecked = true
            binding.rbHostRunner.isChecked = false
            binding.rbPlayerSeeker.isChecked = false
            hostRole.setValue("seeker")
            playerRole.setValue("runner")
        }
        binding.rbPlayerSeeker.setOnClickListener {
            binding.rbHostSeeker.isChecked = false
            binding.rbHostRunner.isChecked = true
            binding.rbPlayerRunner.isChecked = false
            hostRole.setValue("runner")
            playerRole.setValue("seeker")
        }

    }


    override fun onStart() {
        super.onStart()
        playerRef.addValueEventListener(playerEventListener)
    }

    override fun onStop() {
        super.onStop()
        playerRef.removeEventListener(playerEventListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        //db.reference.child("games").child(arguments!!.getString("key")).removeValue()
    }

    fun isOnline(): Boolean {
        val runtime = Runtime.getRuntime()
        try {
            val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
            val exitValue = ipProcess.waitFor()
            return exitValue == 0
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return false
    }

}