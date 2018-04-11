package com.podchody.ui.lobby

import android.arch.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.podchody.NavigationController
import com.podchody.api.AuthFb
import com.podchody.ui.login.LoginViewState
import com.podchody.util.LiveDataDelegate
import com.podchody.util.UiActionsLiveData
import javax.inject.Inject

/**
 * Created by Misiu on 03.04.2018.
 */

class LobbyViewModel
@Inject constructor(
        private val navigationController: NavigationController
) : ViewModel(){

    val liveData = LiveDataDelegate(LobbyViewState())

    private var state by liveData

    val uiActions = UiActionsLiveData()

}