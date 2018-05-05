package com.podchody

/**
 * Created by Misiu on 09.01.2018.
 */

import android.content.Context
import android.support.v4.app.FragmentActivity
import android.view.View

interface NavigationController {
    fun navigateToLogin(activity: FragmentActivity)
    fun navigateToRegister(activity: FragmentActivity)
    fun navigateToLobby(activity: FragmentActivity)
    fun navigateToNewgame(activity: FragmentActivity,title: String,key: String)
    fun navigateToMessageFragment(activity: FragmentActivity)
    fun showError(context: Context, error: String?)
    fun navigateToMapplayerFragment(activity: FragmentActivity,key:String,playerName:String, playerUid: String)
    fun navigateToMapFragment(activity: FragmentActivity,key:String,playerName:String, playerUid: String)
}
