package com.podchody.ui.register

/**
 * Created by Misiu on 10.01.2018.
 */



import android.arch.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.podchody.NavigationController
import com.podchody.PodchodyApp
import com.podchody.util.Coroutines
import com.podchody.util.LiveDataDelegate
import com.podchody.util.UiActionsLiveData
import timber.log.Timber
import javax.inject.Inject

class RegisterViewModel
@Inject constructor(
        private val navigationController: NavigationController,
        private val coroutines: Coroutines
) : ViewModel() {


    val liveData = LiveDataDelegate(RegisterViewState())

    private var state by liveData



    val uiActions = UiActionsLiveData()

    val auth = FirebaseAuth.getInstance()



    fun registerUser() {
        auth.createUserWithEmailAndPassword(state.email, state.password)
                .addOnCompleteListener { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        Timber.d("createUserWithemail : success")
                    } else {
                        Timber.d("createUserWithemail : failure")
                    }
                }
    }
}