package com.example.onto.maps

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.example.onto.R
import com.example.onto.base.BaseFragment
import com.example.onto.base.MviViewModel

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsFragment : BaseFragment<MapsViewState, MapsIntent>(), GoogleMap.OnMarkerClickListener {

    private val markersIntents = MutableLiveData<MapsIntent>()
    private val otherIntents = MutableLiveData<MapsIntent>().also {
        it.value = MapsIntent.InitialIntent
    }

    private lateinit var map: GoogleMap

    override val layoutResourceId: Int = R.layout.fragment_maps

    override val viewModel: MapsViewModel by viewModels()

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        map = googleMap
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun initViews() {
        _intentLiveData.addSource(otherIntents) {
            _intentLiveData.value = it
        }
    }

    override fun render(viewState: MapsViewState) {
        /*for (i in 0..viewState.ontoShopsList!!.size) {
            map.addMarker(
                MarkerOptions().position(
                    LatLng(
                        viewState.ontoShopsList[i].location.latitude.toDouble(),
                        viewState.ontoShopsList[i].location.longitude.toDouble()
                    )
                ).title(viewState.ontoShopsList[i].name)
            )
        }*/
        val a = 1
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        TODO("Not yet implemented")
    }
}