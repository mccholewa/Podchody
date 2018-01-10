package com.podchody.ui.newuser

/**
 * Created by Misiu on 10.01.2018.
 */



import android.arch.lifecycle.ViewModel
import com.podchody.NavigationController
import com.podchody.util.Coroutines
import com.podchody.util.LiveDataDelegate
import com.podchody.util.UiActionsLiveData
import javax.inject.Inject

class NewUserViewModel
@Inject constructor(
        private val navigationController: NavigationController,
        private val coroutines: Coroutines
) : ViewModel(){

    val liveData = LiveDataDelegate(NewUserViewState())

    private var state by liveData

    val uiActions = UiActionsLiveData()

    fun NewUser() = coroutines {
        val login = state.login
        val password = state.password

    }
}