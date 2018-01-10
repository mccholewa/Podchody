package com.podchody

/**
 * Created by Misiu on 09.01.2018.
 */

import android.support.v4.app.FragmentActivity
import com.podchody.ui.login.LoginFragment
import com.podchody.ui.newuser.NewUserFragment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton class NavigationController @Inject constructor() {

    fun navigateToLogin(activity:FragmentActivity) {
        val loginFragment = LoginFragment()
        activity.supportFragmentManager.beginTransaction()
                .replace(R.id.container, loginFragment)
                .commitAllowingStateLoss()
    }

    fun navigateToNewUser(activity: FragmentActivity){
        val newUserFragment = NewUserFragment()
        activity.supportFragmentManager.beginTransaction()
                .replace(R.id.container, newUserFragment)
                .commitAllowingStateLoss()

    }
}