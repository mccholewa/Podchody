package com.podchody.ui.login

/**
 * Created by Misiu on 09.01.2018.
 */

import android.arch.lifecycle.ViewModel
import android.content.SharedPreferences
import com.podchody.NavigationController
import com.podchody.util.Coroutines
import com.podchody.util.LiveDataDelegate
import com.podchody.util.UiActionsLiveData
import java.util.*
import javax.inject.Inject

class LoginViewModel
@Inject constructor(
        private val navigationController: NavigationController,
        private val coroutines: Coroutines
) : ViewModel(){

    val liveData = LiveDataDelegate(LoginViewState())

    private var state by liveData

    val uiActions = UiActionsLiveData()

    fun loginUser() = coroutines {
        val login = state.login
        val password = state.password

    }
}
