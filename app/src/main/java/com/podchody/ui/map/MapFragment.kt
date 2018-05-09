package com.podchody.ui.map


import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.PermissionChecker.checkSelfPermission
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.podchody.NavigationController
import com.podchody.ui.lobby.LobbyViewModel
import com.podchody.util.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import javax.inject.Provider

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.ServerValue
import com.podchody.R


import com.podchody.databinding.MapFragmentBinding
import com.podchody.modle.MarkerFb
import com.podchody.util.OnBackPressedListener
import com.podchody.vo.StringLanguageResource
import kotlinx.android.synthetic.main.addmarker_dialog.view.*
import kotlinx.android.synthetic.main.exitgame_dialog.view.*
import java.io.IOException
import java.util.*


/**
 * Created by Misiu on 18.04.2018.
 */

class MapFragment: Fragment(), OnBackPressedListener,  LocationListener, ActivityCompat.OnRequestPermissionsResultCallback {
    @Inject
    lateinit var viewModelProvider: Provider<LobbyViewModel>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var navigationController: NavigationController

    @Inject
    lateinit var db: FirebaseDatabase

    @Inject
    lateinit var auth: FirebaseAuth

    lateinit var googleMap: GoogleMap
    lateinit var fusedLocationClient : FusedLocationProviderClient

    private var mapFragment: SupportMapFragment? = null
    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 10
    private val language = Locale.getDefault().displayLanguage


    private val viewModel by lazy {
        viewModelFactory(this, viewModelProvider)
    }

    lateinit var binding: MapFragmentBinding

    override fun onLocationChanged(p0: Location?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBackPressed(): Boolean {
        exitDialog()
        return true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)
        checkLocationPremition()
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance()
            mapFragment!!.getMapAsync {
                googleMap = it
                with(googleMap.uiSettings) {
                    isCompassEnabled = true
                    isMyLocationButtonEnabled = true
                }
                if (checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    googleMap.isMyLocationEnabled = true
                    fusedLocationClient.lastLocation.addOnSuccessListener { lastLocalization: Location? ->
                        if (lastLocalization != null) {
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lastLocalization.latitude, lastLocalization.longitude),18F))
                        }
                    }

                }else checkLocationPremition()
            }
        }

        childFragmentManager.beginTransaction().replace(R.id.map, mapFragment).commit()
        binding = MapFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)
        if (checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            binding.btnMarker.setOnClickListener {
                fusedLocationClient.lastLocation.addOnSuccessListener { lastLocalization: Location? ->
                    if (lastLocalization != null) {
                        addMarkerDialog(LatLng(lastLocalization.latitude, lastLocalization.longitude))
                    }
                }
            }
        }else checkLocationPremition()

        //  binding.viewModel = viewModel
        viewModel.liveData.observe(this) {
            //     binding.state = it
        }
        viewModel.uiActions.observe(this) { it(activity!!) }
    }

    fun addMarkerDialog(latLng: LatLng) {
        val dialogBuilder = AlertDialog.Builder(activity!!)
        val dialogView = this.layoutInflater.inflate(R.layout.addmarker_dialog, null)
        dialogBuilder.setView(dialogView)
        val alertDialog = dialogBuilder.create()
        dialogView.btn_add_marker.setOnClickListener {
            if (isOnline()) {
                val message = dialogView.et_message.text.toString()
                val markFb = MarkerFb(message, false, latLng.latitude, latLng.longitude)
                val key = db.reference.child("games").child(arguments!!.getString("key")).child("marks").push().key
                db.reference.child("games").child(arguments!!.getString("key")).child("marks").child(key).setValue(markFb)
                db.reference.child("games").child(arguments!!.getString("key")).child("marks").child(key).child("date").setValue(ServerValue.TIMESTAMP)
                val markerOptions = MarkerOptions()
                markerOptions.position(latLng)
                markerOptions.title(message)
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                googleMap.addMarker(markerOptions)
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                alertDialog.cancel()

            } else navigationController.showError(activity!!, StringLanguageResource(language).noNetwork)
        }
        alertDialog.show()
        dialogView.btn_exit_addmarker.setOnClickListener {
            alertDialog.cancel()
        }
    }

    fun exitDialog() {
        val dialogBuilder = AlertDialog.Builder(activity!!)
        val dialogView = this.layoutInflater.inflate(R.layout.exitgame_dialog, null)
        dialogBuilder.setView(dialogView)
        val alertDialog = dialogBuilder.create()
        dialogView.btn_yes.setOnClickListener {
            db.reference.child("users").child(auth.currentUser!!.uid).child("game").removeValue()
            navigationController.navigateToLobby(activity!!)
            alertDialog.cancel()
        }
        alertDialog.show()
        dialogView.btn_no.setOnClickListener {
            alertDialog.cancel()
        }
    }


    fun checkLocationPremition() {
        if (checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }

    fun isOnline(): Boolean {
        val runtime = Runtime.getRuntime()
        try {
            val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
            val exitValue = ipProcess.waitFor()
            return exitValue == 0
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return false
    }

}