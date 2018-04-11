package com.podchody.ui.register

/**
 * Created by Misiu on 10.01.2018.
 */



import android.arch.lifecycle.ViewModel
import android.support.design.widget.Snackbar
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.podchody.NavigationController
import com.podchody.PodchodyApp
import com.podchody.R
import com.podchody.util.Coroutines
import com.podchody.util.LiveDataDelegate
import com.podchody.util.UiActionsLiveData
import kotlinx.android.synthetic.main.main_activity.*
import timber.log.Timber
import javax.inject.Inject

class RegisterViewModel
@Inject constructor(
        private val navigationController: NavigationController,
        private val coroutines: Coroutines,
        private val authFb: FirebaseAuth
) : ViewModel() {


    val liveData = LiveDataDelegate(RegisterViewState())

    private var state by liveData

    val uiActions = UiActionsLiveData()

    fun registerUser() {
        authFb.createUserWithEmailAndPassword(state.email, state.password)
                .addOnCompleteListener { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        Timber.d("createUserWithemail : success")
                    } else {
                        uiActions { Snackbar.make(it.container, "Nie poprawny email", 3) }
                        Timber.d("createUserWithemail : failure, exception : %s", task.exception)
                    }
                }
    }
}