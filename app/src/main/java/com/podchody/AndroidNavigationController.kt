package com.podchody

/**
 * Created by Misiu on 26.03.2018.
 */
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.Snackbar.LENGTH_LONG
import android.support.design.widget.Snackbar.LENGTH_SHORT
import android.support.v4.app.FragmentActivity
import com.firebase.ui.auth.AuthUI
import com.podchody.ui.lobby.LobbyFragment
import com.podchody.ui.login.LoginFragment
import com.podchody.ui.register.RegisterFragment
import javax.inject.Inject
import javax.inject.Singleton
import java.util.Arrays.asList
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.view.View
import android.widget.Toast
import com.podchody.ui.map.MapFragment
import com.podchody.ui.mapplayer.MapplayerFragment
import com.podchody.ui.newgame.NewgameFragment


@Singleton
class AndroidNavigationController @Inject constructor():NavigationController {

    override fun navigateToMapFragment(activity: FragmentActivity,key:String,playerName:String, playerUid: String) {
        var args: Bundle = Bundle()
        args.putString("playerName",playerName)
        args.putString("key", key)
        val mapFragment = MapFragment()
        mapFragment.arguments = args
        activity.supportFragmentManager.beginTransaction()
                .replace(R.id.container, mapFragment)
//                .addToBackStack("map")
                .commitAllowingStateLoss()
    }

    override fun navigateToMapplayerFragment(activity: FragmentActivity,key:String,playerName:String, playerUid: String) {
        var args: Bundle = Bundle()
        args.putString("hostName",playerName)
        args.putString("key", key)
        val mapplayerFragment = MapplayerFragment()
        mapplayerFragment.arguments = args
        activity.supportFragmentManager.beginTransaction()
                .replace(R.id.container, mapplayerFragment)
//                .addToBackStack("map")
                .commitAllowingStateLoss()
    }

    override fun navigateToMessageFragment(activity: FragmentActivity) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun navigateToNewgame(activity:FragmentActivity,title:String,key:String) {
        var args: Bundle = Bundle()
        args.putString("title", title)
        args.putString("key", key)
        val newgameFragment = NewgameFragment()
        newgameFragment.arguments = args
        activity.supportFragmentManager.beginTransaction()
                .replace(R.id.container, newgameFragment)
//                .addToBackStack("newgame")
                .commitAllowingStateLoss()
    }

    override fun navigateToLogin(activity:FragmentActivity) {
        val loginFragment = LoginFragment()
        activity.supportFragmentManager.beginTransaction()
                .replace(R.id.container, loginFragment)
                .commitAllowingStateLoss()
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
                .commitAllowingStateLoss()
    }

    override fun showError(activity: Context, error: String?) {
        Toast.makeText(activity, error ?:"Error",Toast.LENGTH_LONG).show()
       //val snack = Snackbar.make(view, error ?: "Error", LENGTH_SHORT).show()
    }
}