package com.podchody.ui.login


import android.app.Activity
import android.content.Context
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
import com.podchody.NavigationController
import com.podchody.R
import com.podchody.databinding.LoginFragmentBinding
import com.podchody.util.KeyboardUtil
import com.podchody.util.UiActionsLiveData
import com.podchody.util.ViewModelFactory
import com.podchody.vo.StringLanguageResource
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.login_fragment.view.*
import java.util.*
import javax.inject.Inject
import javax.inject.Provider
import java.util.Arrays.asList
import java.util.Arrays.asList
import java.util.Arrays.asList








class LoginFragment : Fragment() {

    @Inject lateinit var viewModelProvider: Provider<LoginViewModel>

    @Inject lateinit var viewModelFactory: ViewModelFactory

    @Inject lateinit var navigationController:NavigationController

    @Inject lateinit var auth: FirebaseAuth

    val LOGIN_PERMISSION:Int = 1000

   // val uiActions = UiActionsLiveData()

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
        createAuthUI()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


      //  binding.viewModel = viewModel

        viewModel.liveData.observe(this){
      //      binding.state = it
        }

        viewModel.uiActions.observe(this){it(activity!!)}
    }


    fun createAuthUI(){
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
        val language = Locale.getDefault().getDisplayLanguage()
        if (requestCode == LOGIN_PERMISSION) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                navigationController.navigateToLobby(activity!!)
            } else {
                if (response == null) {
                    navigationController.showError(activity!!, StringLanguageResource(language).signInFail )
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

