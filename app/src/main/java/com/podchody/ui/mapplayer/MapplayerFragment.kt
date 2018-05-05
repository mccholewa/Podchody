package com.podchody.ui.mapplayer


import android.Manifest
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.content.PermissionChecker
import android.support.v4.content.PermissionChecker.checkSelfPermission
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.GooglePlayServicesUtil.isGooglePlayServicesAvailable
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.firebase.auth.FirebaseAuth
import com.podchody.NavigationController
import com.podchody.ui.lobby.LobbyViewModel
import com.podchody.util.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import javax.inject.Provider

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import com.podchody.R


import com.podchody.databinding.MapplayerFragmentBinding
import com.podchody.modle.MarkerFb
import com.podchody.modle.PlayerFb
import com.podchody.util.OnBackPressedListener
import com.podchody.util.OnMapAndViewReadyListener
import com.podchody.vo.StringLanguageResource
import kotlinx.android.synthetic.main.exitgame_dialog.view.*
import kotlinx.android.synthetic.main.newgame_dialog.view.*
import timber.log.Timber


/**
 * Created by Misiu on 18.04.2018.
 */

class MapplayerFragment: Fragment(), OnBackPressedListener, LocationListener, ActivityCompat.OnRequestPermissionsResultCallback {
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

    lateinit var language: String

    lateinit var locationRequest: LocationRequest
    lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var lastLocalization: Location
    lateinit var markersChildEventListener: ChildEventListener
    lateinit var markersReference: DatabaseReference

    private var mapFragment: SupportMapFragment? = null
    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 10
    private var currentLastMarker: Marker? = null
    private var notFoundMarkerList = mutableListOf<MarkerFb>()


    private val viewModel by lazy {
        viewModelFactory(this, viewModelProvider)
    }

    lateinit var binding: MapplayerFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        checkLocationPremition()
        createMarkersChildEventListener()
        markersReference = db.reference.child("games").child(arguments!!.getString("key")).child("marks")
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)

        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance()
            mapFragment!!.getMapAsync {
                googleMap = it
                with(googleMap.uiSettings) {
                    googleMap.uiSettings.isCompassEnabled = true
                    googleMap.uiSettings.isMyLocationButtonEnabled = true
                }
                createLocalizationRequest()

                if (checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
                    googleMap.isMyLocationEnabled = true
                    fusedLocationClient.lastLocation.addOnSuccessListener { lastLocalization: Location? ->
                        if (lastLocalization != null)
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lastLocalization.latitude, lastLocalization.longitude), 18F))
                    }
                } else checkLocationPremition()

            }
        }

        childFragmentManager.beginTransaction().replace(R.id.mapplayer, mapFragment).commit()
        binding = MapplayerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun addNotFoundMarker(lastMarker:MarkerFb){
        if(currentLastMarker != null)
            currentLastMarker!!.remove()
        val markerOptions = MarkerOptions()
        markerOptions.position(LatLng(lastMarker.lat!!, lastMarker.lng!!))
                .title(lastMarker.message)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_not_found))
        currentLastMarker = googleMap.addMarker(markerOptions)
    }

    fun addFoundMarker(marker: MarkerFb){
        val markerOptions = MarkerOptions()
        markerOptions.position(LatLng(marker.lat!!, marker.lng!!))
                .title(marker.message)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_done))
        googleMap.addMarker(markerOptions)
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            val locationList: List<Location> = locationResult!!.locations
            if (locationList.isNotEmpty()) {
                lastLocalization = locationList.last()
            }

            if (notFoundMarkerList.isNotEmpty()) {
                notFoundMarkerList.sortBy { markerFb: MarkerFb -> markerFb.date }
                var distance = FloatArray(1)
                Location.distanceBetween(notFoundMarkerList.first().lat!!, notFoundMarkerList.first().lng!!,
                        lastLocalization.latitude, lastLocalization.longitude, distance)
                Timber.d("                                                      distance %s ", distance[0])

                if (distance[0] < 5) {
                    db.reference.child("games").child(arguments!!.getString("key")).child("marks").child(notFoundMarkerList
                            .first().key).child("found").setValue(true).addOnSuccessListener {
                        var checkedMarker = currentLastMarker!!.remove()
                        addFoundMarker(notFoundMarkerList.first())
                        notFoundMarkerList.removeAt(0)
                        if (notFoundMarkerList.isNotEmpty()) {
                            addNotFoundMarker(notFoundMarkerList.first())
                        }
                    }
                }

            }
        }

    }

    fun createMarkersChildEventListener() {
        markersChildEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot?, childName: String?) {
                if (dataSnapshot != null) {
                    val marker: MarkerFb = dataSnapshot.getValue(MarkerFb::class.java)!!
                    marker.key = dataSnapshot.key
                    if (marker.found!! == false) {
                        notFoundMarkerList.add(marker)
                        notFoundMarkerList.sortBy { markerFb: MarkerFb -> markerFb.date  }
                        if(currentLastMarker != null)
                            currentLastMarker!!.remove()
                        val lastMarker = notFoundMarkerList.first()
                        addNotFoundMarker(lastMarker)
                    }
                    if (marker.found!!) {
                        addFoundMarker(marker)
                    }
                }
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot?, childName: String?) {
                if (dataSnapshot != null) {
                }
            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildRemoved(p0: DataSnapshot?) {
            }

            override fun onCancelled(databaseError: DatabaseError?) {
                Timber.d("postMessages:onCancelled:    %s", databaseError!!.toException().toString())
            }
        }
    }

    fun createLocalizationRequest() {
        locationRequest = LocationRequest()
        locationRequest.apply {
            interval = 3000
            fastestInterval = 3000
            priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    override fun onLocationChanged(p0: Location?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBackPressed(): Boolean {
        exitDialog()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        markersReference.addChildEventListener(markersChildEventListener)
        //  binding.viewModel = viewModel
        viewModel.liveData.observe(this) {
            //     binding.state = it
        }
        viewModel.uiActions.observe(this) { it(activity!!) }
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
        if (PermissionChecker.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    navigationController.showError(activity!!, "Location Request granted")
                    if (PermissionChecker.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
                        googleMap.setMyLocationEnabled(true)
                    }
                } else {
                    navigationController.showError(activity!!, "Location Request failed")
                    checkLocationPremition()
                }
                return
            }
        }
    }

    override fun onDestroy() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
        markersReference.removeEventListener(markersChildEventListener)
        super.onDestroy()
    }

}