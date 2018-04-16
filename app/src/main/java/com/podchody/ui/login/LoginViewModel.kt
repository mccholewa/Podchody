package com.podchody.ui.login

/**
 * Created by Misiu on 09.01.2018.
 */

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.podchody.NavigationController
import com.podchody.PodchodyApp
import com.podchody.R
import com.podchody.util.Coroutines
import com.podchody.util.LiveDataDelegate
import com.podchody.util.UiActionsLiveData
import dagger.android.DaggerApplication
import kotlinx.android.synthetic.main.main_activity.*
import timber.log.Timber
import java.security.AccessController.getContext
import javax.inject.Inject

class LoginViewModel
@Inject constructor(
        private val navigationController: NavigationController,
        private val auth: FirebaseAuth
) : ViewModel(){

    val liveData = LiveDataDelegate(LoginViewState())

    private var state by liveData

    val uiActions = UiActionsLiveData()


}
