package com.podchody

/**
 * Created by Misiu on 26.03.2018.
 */
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentActivity
import com.firebase.ui.auth.AuthUI
import com.podchody.ui.lobby.LobbyFragment
import com.podchody.ui.login.LoginFragment
import com.podchody.ui.register.RegisterFragment
import javax.inject.Inject
import javax.inject.Singleton
import java.util.Arrays.asList
import android.support.v4.app.ActivityCompat.startActivityForResult



@Singleton
class AndroidNavigationController @Inject constructor():NavigationController {

    override fun navigateToLogin(activity:FragmentActivity) {
        val loginFragment = LoginFragment()
        activity.supportFragmentManager.beginTransaction()
                .replace(R.id.container, loginFragment)
                .commitAllowingStateLoss()
    }

    override fun navigateToAuth(activity: FragmentActivity){
        val RC_SIGN_IN:Int = 123


    }

   override fun navigateToRegister(activity: FragmentActivity){
        val registerFragment = RegisterFragment()
        activity.supportFragmentManager.beginTransaction()
                .replace(R.id.container, registerFragment)
                .addToBackStack(null)
                .commitAllowingStateLoss()
    }

    override fun navigateToLobby(activity: FragmentActivity){
        val lobbyFragment = LobbyFragment()
        activity.supportFragmentManager.beginTransaction()
                .replace(R.id.container,lobbyFragment)
                .addToBackStack(null)
                .commitAllowingStateLoss()
    }

    override fun showError(activity: FragmentActivity, error: String?) {
        Snackbar.make(activity.findViewById(android.R.id.content), error
                ?: "Error", Snackbar.LENGTH_LONG).show()
    }
}