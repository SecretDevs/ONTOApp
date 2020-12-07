package com.example.onto.maps

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.onto.R
import com.example.onto.base.BaseFragment
import com.example.onto.vo.remote.OntoShop
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.model.BitmapDescriptorFactory
import com.google.android.libraries.maps.model.CameraPosition
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_maps.*
import kotlinx.android.synthetic.main.item_error.*
import timber.log.Timber

@AndroidEntryPoint
class MapsFragment : BaseFragment<MapsViewState, MapsIntent>() {
    override val layoutResourceId: Int = R.layout.fragment_maps
    override val viewModel: MapsViewModel by viewModels()

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var shopInfoWindowAdapter: ShopInfoWindowAdapter

    private val defaultLocation = LatLng(55.7558, 37.6173)
    private var locationPermissionGranted = false

    private var cameraPosition: CameraPosition? = null
    private var lastKnownLocation: Location? = null
    private var savedInstanceState: Bundle? = null

    override fun backStackIntent(): MapsIntent = MapsIntent.MapsNothingIntent
    override fun initialIntent(): MapsIntent? = MapsIntent.PermissionsCheckIntent

    override fun initViews() {
        shopInfoWindowAdapter = ShopInfoWindowAdapter(requireContext())
        retry_button.setOnClickListener {
            _intentLiveData.value = MapsIntent.ReloadIntent
        }
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this.requireActivity())
    }

    override fun render(viewState: MapsViewState) {
        if (!viewState.isPermissionsChecked) {
            getLocationPermission()
        }

        loading_view.isVisible = viewState.isInitialLoading || !viewState.isMapLoaded
        error_view.isVisible = viewState.initialError != null
        shopInfoWindowAdapter.updateShops(viewState.ontoShopsList)

        if (viewState.ontoShopsList.isNotEmpty()) {
            map_view.isVisible = true
            if (!this@MapsFragment::map.isInitialized) {
                map_view.getMapAsync {
                    this@MapsFragment.map = it
                    it.setInfoWindowAdapter(shopInfoWindowAdapter)
                    _intentLiveData.value = MapsIntent.MapLoadedIntent
                    it.setOnMarkerClickListener { marker ->
                        marker.showInfoWindow()
                        val cameraPosition = CameraPosition.Builder()
                            .target(marker.position)
                            .zoom(15F)
                            .bearing(0F)
                            .tilt(25F)
                            .build()
                        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                        true
                    }
                    loadMap()
                    addMarkers(viewState.ontoShopsList)
                }
            } else {
                loadMap()
                addMarkers(viewState.ontoShopsList)
            }
        } else {
            map_view.isVisible = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION)
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.savedInstanceState = savedInstanceState
        map_view.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        map.let { map ->
            outState.putParcelable(KEY_CAMERA_POSITION, map.cameraPosition)
            outState.putParcelable(KEY_LOCATION, lastKnownLocation)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
        map_view.onResume()
    }

    override fun onPause() {
        super.onPause()
        map_view.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        map_view.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        map_view.onLowMemory()
    }

    private fun loadMap() {
        updateLocationUI()
        getDeviceLocation()
    }

    private fun getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(this.requireActivity()) { task ->
                    if (task.isSuccessful) {
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            map.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(
                                        lastKnownLocation!!.latitude,
                                        lastKnownLocation!!.longitude
                                    ), CURRENT_ZOOM
                                )
                            )
                        }
                    } else {
                        map.moveCamera(
                            CameraUpdateFactory
                                .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat())
                        )
                        map.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            } else {
                map.moveCamera(
                    CameraUpdateFactory
                        .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat())
                )
            }
        } catch (e: SecurityException) {
            Timber.e(e)
        }
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this.requireContext().applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
            _intentLiveData.value = MapsIntent.InitialIntent
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    locationPermissionGranted = true
                    map_view.onCreate(savedInstanceState)
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
        _intentLiveData.value = MapsIntent.InitialIntent
    }

    private fun updateLocationUI() {
        if (!this::map.isInitialized) {
            return
        }
        try {
            if (locationPermissionGranted) {
                map.isMyLocationEnabled = true
                map.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                map.isMyLocationEnabled = false
                map.uiSettings?.isMyLocationButtonEnabled = true
                lastKnownLocation = null
            }
        } catch (e: SecurityException) {
            Timber.e(e)
        }
    }

    private fun addMarkers(shopsList: List<OntoShop>) {
        map.setInfoWindowAdapter(shopInfoWindowAdapter)
        shopsList.forEach(::createShopMarker)
    }

    private fun createShopMarker(shop: OntoShop) {
        val shopLocation = LatLng(shop.location.latitude, shop.location.longitude)
        map.addMarker(
            MarkerOptions()
                .position(shopLocation)
                .icon(BitmapDescriptorFactory.fromBitmap(getMarkerIconAsBitmap()))
        )
    }

    private fun getMarkerIconAsBitmap(): Bitmap? =
        ContextCompat
            .getDrawable(requireContext(), R.drawable.ic_map_marker)
            ?.run { toBitmap(intrinsicWidth, intrinsicHeight) }

    companion object {
        private val TAG = "Maps"
        private const val DEFAULT_ZOOM = 10F
        private const val CURRENT_ZOOM = 12F
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"

        fun newInstance() = MapsFragment()
    }
}