package com.example.onto.maps

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.onto.R
import com.example.onto.base.BaseFragment
import com.example.onto.vo.OntoShop
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.model.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_maps.*


@AndroidEntryPoint
class MapsFragment : BaseFragment<MapsViewState, MapsIntent>(), GoogleMap.OnMarkerClickListener,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener {

    private val intentLiveData = MutableLiveData<MapsIntent>().also { intents ->
        intents.value = MapsIntent.PermissionsCheckIntent
        _intentLiveData.addSource(intents) {
            _intentLiveData.value = it
        }
    }

    private var map: GoogleMap? = null
    private var cameraPosition: CameraPosition? = null

    // The entry point to the Fused Location Provider.
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private val defaultLocation = LatLng(59.9342802, 30.3350986)
    private var locationPermissionGranted = false

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private var lastKnownLocation: Location? = null

    private var savedInstanceState: Bundle? = null

    override val layoutResourceId: Int = R.layout.fragment_maps

    override val viewModel: MapsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION)
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION)
        }

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this.requireActivity())

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.savedInstanceState = savedInstanceState
        mapView.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        map?.let { map ->
            outState.putParcelable(KEY_CAMERA_POSITION, map.cameraPosition)
            outState.putParcelable(KEY_LOCATION, lastKnownLocation)
        }
        super.onSaveInstanceState(outState)
    }

    override fun initViews() {
        retry_button.setOnClickListener {
            intentLiveData.value = MapsIntent.ReloadIntent
        }
    }

    override fun render(viewState: MapsViewState) {
        when {
            !viewState.isPermissionsChecked -> {
                getLocationPermission()
            }
            viewState.isInitialLoading -> {
                mapView.isVisible = false
                errorLayout.isVisible = false
                mapsProgressBar.isVisible = true
            }
            viewState.initialError != null -> {
                mapView.isVisible = false
                mapsProgressBar.isVisible = false
                errorLayout.isVisible = true
            }
            viewState.ontoShopsList.isEmpty() -> {
                mapView.getMapAsync {
                    this.map = it

                    loadMap()

                    mapsProgressBar.isVisible = false
                    errorLayout.isVisible = false
                    mapView.isVisible = true
                }
            }
            else -> {
                mapView.getMapAsync {
                    this.map = it

                    loadMap()

                    addMarkers(viewState.ontoShopsList)

                    mapsProgressBar.isVisible = false
                    errorLayout.isVisible = false
                    mapView.isVisible = true
                }
            }
        }
    }

    private fun loadMap(){

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI()

        // Get the current location of the device and set the position of the map.
        getDeviceLocation()
    }


    /**
     * Gets the current location of the device, and positions the map's camera.
     */
    private fun getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(this.requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            Log.d(TAG, "Current location is ${lastKnownLocation}. Using defaults.")
                            map?.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(
                                        lastKnownLocation!!.latitude,
                                        lastKnownLocation!!.longitude
                                    ), CURRENT_ZOOM.toFloat()
                                )
                            )
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.")
                        Log.e(TAG, "Exception: %s", task.exception)
                        map?.moveCamera(
                            CameraUpdateFactory
                                .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat())
                        )
                        map?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }else{
                map?.moveCamera(
                    CameraUpdateFactory
                        .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat())
                )
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    /**
     * Prompts the user for permission to use the device location.
     */
    private fun getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(
                this.requireContext().applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
            intentLiveData.value = MapsIntent.InitialIntent
        }else{
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    /**
     * Handles the result of the request for location permissions.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    locationPermissionGranted = true
                    mapView.onCreate(savedInstanceState)
                } else {
                    val toast = makeText(
                        requireContext(),
                        resources.getString(R.string.shops_warning),
                        Toast.LENGTH_LONG
                    )
                    toast.show()
                }
            }
        }
        intentLiveData.value = MapsIntent.InitialIntent
    }

    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    private fun updateLocationUI() {
        if (map == null) {
            return
        }
        try {
            if (locationPermissionGranted) {
                map?.isMyLocationEnabled = true
                map?.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                map?.isMyLocationEnabled = false
                map?.uiSettings?.isMyLocationButtonEnabled = true
                lastKnownLocation = null
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    private fun addMarkers(shopsList: List<OntoShop>){
        if (map != null) {
            map!!.setInfoWindowAdapter(CustomInfoWindowForGoogleMap(requireActivity()))
            for (shop in shopsList) {
                Glide.with(requireContext())
                    .asBitmap()
                    .load(shop.partner.logo)
                    .into(object : CustomTarget<Bitmap>(50, 50) {
                        override fun onLoadCleared(placeholder: Drawable?) {

                        }

                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            map?.addMarker(
                                MarkerOptions().position(
                                    LatLng(
                                        shop.location.latitude.toDouble(),
                                        shop.location.longitude.toDouble()
                                    )
                                ).title(shop.name)
                                    .snippet("Адрес: ${shop.address}\nПартнер: ${shop.partner.name}")
                                    .icon(BitmapDescriptorFactory.fromBitmap(resource))
                            )
                        }
                    })
            }
        }
    }

    //Marker OnClick method. May be it will be need for custom markers.
    override fun onMarkerClick(p0: Marker?): Boolean {
        TODO("Not yet implemented")
    }


    /**
     * Marker OnClick methods. May be they will be need for custom markers.
     */
    //[START Marker OnClick methods]
    override fun onMyLocationButtonClick(): Boolean {
        makeText(this.requireContext(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    override fun onMyLocationClick(p0: Location) {
        makeText(this.requireContext(), "Current location:\n" + p0, Toast.LENGTH_LONG).show();
    }
    //[END Marker OnClick methods]


    /**
     * Overriding fragment methods for map lifecycle
     */
    //[START Overriding fragment methods for map lifecycle]
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
    //[END Overriding fragment methods for map lifecycle]


    /**
     * Constants for some map actions, such as zooming or getting access for device location.
     */
    companion object {
        private val TAG = "Maps"
        private const val DEFAULT_ZOOM = 10
        private const val CURRENT_ZOOM = 12
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

        // Keys for storing activity state.
        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"
    }

}