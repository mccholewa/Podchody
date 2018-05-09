package com.podchody

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.PermissionChecker
import android.view.Menu
import android.view.MenuItem
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.podchody.R.layout.main_activity
import com.podchody.api.FirebaseDatabaseRepository
import com.podchody.vo.StringLanguageResource
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import com.google.android.gms.maps.SupportMapFragment
import com.google.firebase.database.*
import com.podchody.ui.map.MapFragment
import com.podchody.ui.mapplayer.MapplayerFragment
import com.podchody.ui.newgame.NewgameFragment
import com.podchody.ui.newgameplayer.NewgameplayerFragment
import com.podchody.util.OnBackPressedListener


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector, ActivityCompat.OnRequestPermissionsResultCallback {

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var navigationController: NavigationController

    @Inject
    lateinit var auth: FirebaseAuth

    @Inject
    lateinit var db: FirebaseDatabase

    @Inject
    lateinit var repository : FirebaseDatabaseRepository


    val LOGIN_PERMISSION:Int = 123
    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 10
    private val PLAY_SERVICES_RESOLUTION_REQUEST = 30
    private val REQUEST_CHECK_SETTINGS = 20
    private val REQUEST_LOCATION = 199


    lateinit var stringLanguageResource: StringLanguageResource


    override fun onBackPressed() {
        val fragment = this.supportFragmentManager.findFragmentById(R.id.container)

        if (fragment is NewgameFragment)
            if (!fragment.onBackPressed())
                super.onBackPressed()
            else return


        if (fragment is NewgameplayerFragment)
            if (!fragment.onBackPressed())
                super.onBackPressed()
            else return


        if (fragment is MapFragment)
            if (!fragment.onBackPressed())
                super.onBackPressed()
            else return

        if (fragment is MapplayerFragment)
            if (!fragment.onBackPressed())
                super.onBackPressed()
            else return

        super.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    //     navigationController.navigateToMapFragment(this,"key","title")

        if (savedInstanceState == null) {
        if(auth.currentUser == null)
            navigationController.navigateToLogin(this)
       else {
            navigationController.navigateToLobby(this)
        }
        }


    }


//    override fun onBackPressed() {
//        val drawer: DrawerLayout = findViewById(R.id.container)
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START)
//        } else {
//            val fragments = supportFragmentManager.backStackEntryCount
//            if (fragments == 1) {
//                finish()
//            } else {
//                if (fragmentManager.backStackEntryCount > 1) {
//                    fragmentManager.popBackStack()
//                } else {
//                    super.onBackPressed()
//                }
//            }
//        }
//    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.actionbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_sing_out -> {
                repository.setUserOffline()
                AuthUI.getInstance().signOut(this).addOnCompleteListener {
                    if (it.isSuccessful) {
                        navigationController.navigateToLogin(this)
                        Timber.d("Logged out")
                    }
                }
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }


    fun clearStack() {
        //Here we are clearing back stack fragment entries
        val backStackEntry = supportFragmentManager.backStackEntryCount
        if (backStackEntry > 0) {
            for (i in 0 until backStackEntry) {
                supportFragmentManager.popBackStackImmediate()
            }
        }

        //Here we are removing all the fragment that are shown here
        if (supportFragmentManager.fragments != null && supportFragmentManager.fragments.size > 0) {
            for (i in 0 until supportFragmentManager.fragments.size) {
                val mFragment = supportFragmentManager.fragments[i]
                if (mFragment != null) {
                    supportFragmentManager.beginTransaction().remove(mFragment).commit()
                }
            }
        }
    }

    fun createAuthUI(){
        Timber.d("                                                                                      Auth ui started")
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(true)
                        .setAvailableProviders(listOf(
                                AuthUI.IdpConfig.EmailBuilder().build(),
                                AuthUI.IdpConfig.GoogleBuilder().build()
                        )).setTheme(R.style.AppTheme)
                        .build(), LOGIN_PERMISSION)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    navigationController.showError(this,"Location Request granted")
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    navigationController.showError(this,"Location Request failed")
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    checkLocationPremition()
                }
                return
            }
        // Add other 'when' lines to check for other
        // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }
    fun checkLocationPremition() {
        if (PermissionChecker.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
//        super.onActivityResult(requestCode, resultCode, data)
//        val language = Locale.getDefault().displayLanguage
//        if (requestCode == LOGIN_PERMISSION) {
//            val response = IdpResponse.fromResultIntent(data)
//            if (resultCode == Activity.RESULT_OK) {
//                repository.writeUser()
//                val currentUserReference = db.reference.child("users").child(auth.currentUser!!.uid).child("connected")
//                currentUserReference!!.onDisconnect().setValue(false)
//                clearStack()
//                navigationController.navigateToLobby(this)
//            } else {
//                if (response == null) {
//                    navigationController.showError( this.findViewById(R.id.container), StringLanguageResource(language).signInFail )
//                    return
//                }
//                if (response.error!!.errorCode == ErrorCodes.NO_NETWORK) {
//                    navigationController.showError(this.findViewById(R.id.container), StringLanguageResource(language).noNetwork)
//                    return
//                }
//                navigationController.showError(this.findViewById(R.id.container), StringLanguageResource(language).unknownError)
//            }
//        }
//    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentDispatchingAndroidInjector

}
