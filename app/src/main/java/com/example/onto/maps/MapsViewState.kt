package com.example.onto.maps

import com.example.onto.base.MviViewState
import com.example.onto.vo.OntoShop

class MapsViewState(
    val isPermissionsChecked: Boolean,
    val isInitialLoading: Boolean,
    val initialError: Throwable?,
    val ontoShopsList: List<OntoShop>
) : MviViewState {
    companion object{
        val permissionsCheckState = MapsViewState(
            isPermissionsChecked = false,
            isInitialLoading = false,
            initialError = null,
            ontoShopsList = emptyList()
        )

        val initialState = MapsViewState(
            isPermissionsChecked = true,
            isInitialLoading = false,
            initialError = null,
            ontoShopsList = emptyList()
        )

        val initialLoadingState = MapsViewState(
            isPermissionsChecked = true,
            isInitialLoading = true,
            initialError = null,
            ontoShopsList = emptyList()
        )

        fun initialErrorState(error: Throwable): MapsViewState = MapsViewState(
            isPermissionsChecked = true,
            isInitialLoading = false,
            initialError = error,
            ontoShopsList = emptyList()
        )

        fun shopsLoadedState(shops: List<OntoShop>): MapsViewState = MapsViewState(
            isPermissionsChecked = true,
            isInitialLoading = false,
            initialError = null,
            ontoShopsList = shops
        )

    }
}