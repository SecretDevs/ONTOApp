package com.example.onto.maps

import android.graphics.Bitmap
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.example.onto.R
import com.example.onto.base.BaseFragment
import com.example.onto.vo.OntoShop
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_maps.*
import kotlinx.android.synthetic.main.item_error.*


@AndroidEntryPoint
class MapsFragment : BaseFragment<MapsViewState, MapsIntent>() {
    override val layoutResourceId: Int = R.layout.fragment_maps
    override val viewModel: MapsViewModel by viewModels()

    private lateinit var shopInfoWindowAdapter: ShopInfoWindowAdapter
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var googleMap: GoogleMap

    private var lastMarker: Marker? = null

    private val intentLiveData = MutableLiveData<MapsIntent>().also { intents ->
        _intentLiveData.addSource(intents) {
            _intentLiveData.value = it
        }
    }

    override fun initialIntent(): MapsIntent? = MapsIntent.InitialIntent

    override fun initViews() {
        mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        shopInfoWindowAdapter = ShopInfoWindowAdapter(requireActivity())

        retry_button.setOnClickListener {
            intentLiveData.value = MapsIntent.ReloadIntent
        }
    }

    override fun render(viewState: MapsViewState) {
        loading_view.isVisible = viewState.isInitialLoading
        error_view.isVisible = viewState.initialError != null
        childFragmentManager.beginTransaction().apply {
            if (viewState.ontoShopsList.isNotEmpty()) {
                show(mapFragment).commitAllowingStateLoss()
                if (!this@MapsFragment::googleMap.isInitialized) {
                    mapFragment.getMapAsync {
                        this@MapsFragment.googleMap = it
                        it.setInfoWindowAdapter(shopInfoWindowAdapter)
                        intentLiveData.value = MapsIntent.MapLoadedIntent
                        it.setOnMarkerClickListener { marker ->
                            lastMarker = marker
                            intentLiveData.value =
                                MapsIntent.ShowShopInfoIntent(marker.position.run { latitude to longitude })
                            true
                        }
                    }
                }
            } else {
                hide(mapFragment).commitAllowingStateLoss()
            }
        }

        if (this::googleMap.isInitialized && viewState.ontoShopsList.isNotEmpty()) {
            viewState.ontoShopsList.forEach(::createShopMarker)
        }
        if (viewState.selectedShop != null) {
            shopInfoWindowAdapter.shop = viewState.selectedShop
            lastMarker?.showInfoWindow()
        }
    }

    private fun createShopMarker(shop: OntoShop) {
        val shopLocation = LatLng(shop.location.latitude, shop.location.longitude)
        googleMap.addMarker(
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
        private const val DEFAULT_ZOOM = 10
        private const val CURRENT_ZOOM = 12
    }

}