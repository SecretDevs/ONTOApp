package com.example.onto.maps

import com.example.onto.base.MviViewState
import com.example.onto.vo.remote.OntoShop

class MapsViewState(
    val isMapLoaded: Boolean,
    val isPermissionsChecked: Boolean,
    val isInitialLoading: Boolean,
    val initialError: Throwable?,
    val ontoShopsList: List<OntoShop>
) : MviViewState {
    companion object {
        val permissionsCheckState = MapsViewState(
            isMapLoaded = false,
            isPermissionsChecked = false,
            isInitialLoading = false,
            initialError = null,
            ontoShopsList = emptyList()
        )

        val initialState = MapsViewState(
            isMapLoaded = false,
            isPermissionsChecked = true,
            isInitialLoading = false,
            initialError = null,
            ontoShopsList = emptyList()
        )

        val initialLoadingState = MapsViewState(
            isMapLoaded = false,
            isPermissionsChecked = true,
            isInitialLoading = true,
            initialError = null,
            ontoShopsList = emptyList()
        )

        fun mapLoaded(
            isInitialLoading: Boolean,
            initialError: Throwable?,
            ontoShopsList: List<OntoShop>
        ): MapsViewState =
            MapsViewState(
                isMapLoaded = true,
                isPermissionsChecked = true,
                isInitialLoading = isInitialLoading,
                initialError = initialError,
                ontoShopsList = ontoShopsList
            )

        fun initialErrorState(isMapLoaded: Boolean, error: Throwable): MapsViewState =
            MapsViewState(
                isMapLoaded = isMapLoaded,
                isPermissionsChecked = true,
                isInitialLoading = false,
                initialError = error,
                ontoShopsList = emptyList()
            )

        fun shopsLoadedState(
            isMapLoaded: Boolean,
            shops: List<OntoShop>
        ): MapsViewState = MapsViewState(
            isMapLoaded = isMapLoaded,
            isPermissionsChecked = true,
            isInitialLoading = false,
            initialError = null,
            ontoShopsList = shops
        )

    }

    override fun log(): String = toString()
}