package com.podchody.ui.login


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.podchody.NavigationController
import com.podchody.R
import com.podchody.api.FirebaseDatabaseRepository
import com.podchody.databinding.LoginFragmentBinding
import com.podchody.util.ViewModelFactory
import com.podchody.vo.StringLanguageResource
import dagger.android.support.AndroidSupportInjection
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import javax.inject.Provider


class LoginFragment : Fragment() {

    @Inject lateinit var viewModelProvider: Provider<LoginViewModel>

    @Inject lateinit var viewModelFactory: ViewModelFactory

    @Inject lateinit var navigationController:NavigationController

    @Inject lateinit var auth: FirebaseAuth

    @Inject lateinit var repository: FirebaseDatabaseRepository

    @Inject
    lateinit var db: FirebaseDatabase

    val LOGIN_PERMISSION:Int = 123


    private val viewModel by lazy {
        viewModelFactory(this, viewModelProvider)
    }

    lateinit var binding: LoginFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createAuthUI()

      //  binding.viewModel = viewModel

        viewModel.liveData.observe(this){
       //     binding.state = it
        }

        viewModel.uiActions.observe(this){it(activity!!)}
    }

    fun createAuthUI(){
        Timber.d("                                                                                      Auth ui started")
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(true)
                        .setAvailableProviders(listOf(
                                AuthUI.IdpConfig.EmailBuilder().build(),
                                AuthUI.IdpConfig.GoogleBuilder().build()
                        )).setTheme(R.style.AppTheme)
                        .build(), LOGIN_PERMISSION)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        val language = Locale.getDefault().displayLanguage
        if (requestCode == LOGIN_PERMISSION) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                repository.writeUser()
                val currentUserReference = db.reference.child("users").child(auth.currentUser!!.uid).child("connected")
                currentUserReference!!.onDisconnect().setValue(false)
                db.reference.child("games").child("Game Key").child("host").child("uid").setValue(auth.currentUser!!.uid)
                db.reference.child("games").child("Game Key").child("host").child("role").setValue("runner")
                db.reference.child("games").child("Game Key").child("player").child("uid").setValue("user3")
                db.reference.child("games").child("Game Key").child("player").child("role").setValue("seeker")
                db.reference.child("games").child("Game Key").child("title").setValue("test game")
                navigationController.navigateToMapplayerFragment(activity!!,"Game Key","Andrzej Gąska","user3")
                //navigationController.navigateToLobby(activity!!)
            } else {
                if (response == null) {
                    navigationController.showError( activity!!, StringLanguageResource(language).signInFail )
                    return
                }
                if (response.error!!.errorCode == ErrorCodes.NO_NETWORK) {
                    navigationController.showError(activity!!, StringLanguageResource(language).noNetwork)
                    return
                }
                navigationController.showError(activity!!, StringLanguageResource(language).unknownError)
            }
        }
    }
}

