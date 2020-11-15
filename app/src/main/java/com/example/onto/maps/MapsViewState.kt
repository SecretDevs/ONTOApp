package com.example.onto.maps

import com.example.onto.base.MviViewState
import com.example.onto.vo.OntoShop

class MapsViewState(
    val isMapLoading: Boolean,
    val isInitialLoading: Boolean,
    val initialError: Throwable?,
    val ontoShopsList: List<OntoShop>,
    val selectedShop: OntoShop?
) : MviViewState {
    companion object {
        val initialState: MapsViewState = MapsViewState(
            isMapLoading = true,
            isInitialLoading = true,
            initialError = null,
            ontoShopsList = emptyList(),
            selectedShop = null
        )

        fun mapLoadedState(
            isInitialLoading: Boolean,
            initialError: Throwable?,
            ontoShopsList: List<OntoShop>,
            selectedShop: OntoShop?
        ): MapsViewState = MapsViewState(
            isMapLoading = false,
            isInitialLoading = isInitialLoading,
            initialError = initialError,
            ontoShopsList = ontoShopsList,
            selectedShop = selectedShop
        )

        fun initialLoadingState(isMapLoading: Boolean): MapsViewState = MapsViewState(
            isMapLoading = isMapLoading,
            isInitialLoading = true,
            initialError = null,
            ontoShopsList = emptyList(),
            selectedShop = null
        )

        fun initialLoadingErrorState(
            isMapLoading: Boolean,
            initialError: Throwable?
        ): MapsViewState = MapsViewState(
            isMapLoading = isMapLoading,
            isInitialLoading = false,
            initialError = initialError,
            ontoShopsList = emptyList(),
            selectedShop = null
        )

        fun shopLoadedState(
            isMapLoading: Boolean,
            ontoShopsList: List<OntoShop>
        ): MapsViewState = MapsViewState(
            isMapLoading = isMapLoading,
            isInitialLoading = false,
            initialError = null,
            ontoShopsList = ontoShopsList,
            selectedShop = null
        )

        fun shopInfoState(
            isMapLoading: Boolean,
            ontoShopsList: List<OntoShop>,
            selectedShop: OntoShop?
        ): MapsViewState = MapsViewState(
            isMapLoading = isMapLoading,
            isInitialLoading = false,
            initialError = null,
            ontoShopsList = ontoShopsList,
            selectedShop = selectedShop
        )

    }
}