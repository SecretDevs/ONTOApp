package com.example.onto.maps

import com.example.onto.base.MviViewState
import com.example.onto.vo.OntoShop

class MapsViewState(
    val isInitialLoading: Boolean,
    val initialError: Throwable?,
    val ontoShopsList: List<OntoShop>
) : MviViewState {
    companion object{
        val initialState = MapsViewState(
            isInitialLoading = false,
            initialError = null,
            ontoShopsList = emptyList()
        )

        val initialLoadingState = MapsViewState(
            isInitialLoading = true,
            initialError = null,
            ontoShopsList = emptyList()
        )

        fun initialErrorState(error: Throwable): MapsViewState = MapsViewState(
            isInitialLoading = false,
            initialError = error,
            ontoShopsList = emptyList()
        )

        fun shopsLoadedState(shops: List<OntoShop>): MapsViewState = MapsViewState(
            isInitialLoading = false,
            initialError = null,
            ontoShopsList = shops
        )

    }
}