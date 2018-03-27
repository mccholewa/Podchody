package com.podchody.ui.login

/**
 * Created by Misiu on 09.01.2018.
 */

import android.arch.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.podchody.NavigationController
import com.podchody.util.Coroutines
import com.podchody.util.LiveDataDelegate
import com.podchody.util.UiActionsLiveData
import javax.inject.Inject

class LoginViewModel
@Inject constructor(
        private val navigationController: NavigationController,
        private val coroutines: Coroutines
) : ViewModel(){

    val liveData = LiveDataDelegate(LoginViewState())

    private var state by liveData

    val uiActions = UiActionsLiveData()

    val auth = FirebaseAuth.getInstance()


    fun openRegister() = uiActions { navigationController.navigateToRegister(it) }

}
