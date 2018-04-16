package com.podchody

/**
 * Created by Misiu on 09.01.2018.
 */

import android.support.v4.app.FragmentActivity

interface NavigationController {
    fun navigateToLogin(activity: FragmentActivity)
    fun navigateToRegister(activity: FragmentActivity)
    fun navigateToLobby(activity: FragmentActivity)
    fun showError(activity: FragmentActivity, error: String?)
}
