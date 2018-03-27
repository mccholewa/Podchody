package com.podchody

/**
 * Created by Misiu on 26.03.2018.
 */
import android.support.v4.app.FragmentActivity
import com.podchody.ui.login.LoginFragment
import com.podchody.ui.register.RegisterFragment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton class AndroidNavigationController @Inject constructor():NavigationController {

    override fun navigateToLogin(activity:FragmentActivity) {
        val loginFragment = LoginFragment()
        activity.supportFragmentManager.beginTransaction()
                .replace(R.id.container, loginFragment)
                .commitAllowingStateLoss()
    }


   override fun navigateToRegister(activity: FragmentActivity){
        val newuserFragment = RegisterFragment()
        activity.supportFragmentManager.beginTransaction()
                .replace(R.id.container, newuserFragment)
                .commitAllowingStateLoss()

    }
}