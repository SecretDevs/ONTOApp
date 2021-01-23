package com.example.onto.maps

import com.example.onto.base.MviViewState
import com.example.onto.vo.inapp.CartInformation
import com.example.onto.vo.remote.OntoShop

class MapsViewState(
    val cartInformation: CartInformation? = null,
    val isMapLoaded: Boolean,
    val isPermissionsChecked: Boolean,
    val isInitialLoading: Boolean,
    val initialError: Throwable?,
    val ontoShopsList: List<OntoShop>
) : MviViewState {
    companion object {
        val permissionsCheckState = MapsViewState(
            cartInformation = null,
            isMapLoaded = false,
            isPermissionsChecked = false,
            isInitialLoading = false,
            initialError = null,
            ontoShopsList = emptyList()
        )

        fun initialState(cartInformation: CartInformation?) = MapsViewState(
            cartInformation = cartInformation,
            isMapLoaded = false,
            isPermissionsChecked = true,
            isInitialLoading = false,
            initialError = null,
            ontoShopsList = emptyList()
        )

        fun initialLoadingState(cartInformation: CartInformation?) = MapsViewState(
            cartInformation = cartInformation,
            isMapLoaded = false,
            isPermissionsChecked = true,
            isInitialLoading = true,
            initialError = null,
            ontoShopsList = emptyList()
        )

        fun mapLoaded(
            cartInformation: CartInformation?,
            isInitialLoading: Boolean,
            initialError: Throwable?,
            ontoShopsList: List<OntoShop>
        ): MapsViewState =
            MapsViewState(
                cartInformation = cartInformation,
                isMapLoaded = true,
                isPermissionsChecked = true,
                isInitialLoading = isInitialLoading,
                initialError = initialError,
                ontoShopsList = ontoShopsList
            )

        fun initialErrorState(
            isMapLoaded: Boolean,
            error: Throwable,
            cartInformation: CartInformation?
        ): MapsViewState =
            MapsViewState(
                cartInformation = cartInformation,
                isMapLoaded = isMapLoaded,
                isPermissionsChecked = true,
                isInitialLoading = false,
                initialError = error,
                ontoShopsList = emptyList()
            )

        fun shopsLoadedState(
            isMapLoaded: Boolean,
            shops: List<OntoShop>,
            cartInformation: CartInformation?
        ): MapsViewState = MapsViewState(
            cartInformation = cartInformation,
            isMapLoaded = isMapLoaded,
            isPermissionsChecked = true,
            isInitialLoading = false,
            initialError = null,
            ontoShopsList = shops
        )

        fun cartInformationLoaded(
            cartInformation: CartInformation?,
            isMapLoaded: Boolean,
            isPermissionsChecked: Boolean,
            isInitialLoading: Boolean,
            initialError: Throwable?,
            ontoShopsList: List<OntoShop>
        ): MapsViewState = MapsViewState(
            cartInformation = cartInformation,
            isMapLoaded = isMapLoaded,
            isPermissionsChecked = isPermissionsChecked,
            isInitialLoading = isInitialLoading,
            initialError = initialError,
            ontoShopsList = ontoShopsList
        )

    }

    override fun log(): String = toString()
}