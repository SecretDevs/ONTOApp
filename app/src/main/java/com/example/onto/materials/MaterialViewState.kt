package com.example.onto.materials

import com.example.onto.base.MviViewState
import com.example.onto.vo.inapp.CartInformation
import com.example.onto.vo.remote.OntoArticle

data class MaterialViewState(
    val isInitialLoading: Boolean,
    val initialError: Throwable?,
    val products: List<OntoArticle>,
    val cartInformation: CartInformation?,
    val isRefreshLoading: Boolean,
    val refreshError: Throwable?
) : MviViewState {

    override fun log(): String = this.toString()

    companion object {
        val initialState = MaterialViewState(
            isInitialLoading = false,
            initialError = null,
            products = emptyList(),
            cartInformation = null,
            isRefreshLoading = false,
            refreshError = null
        )

        val initialLoadingState = MaterialViewState(
            isInitialLoading = true,
            initialError = null,
            products = emptyList(),
            cartInformation = null,
            isRefreshLoading = false,
            refreshError = null
        )

        fun cartInformationLoaded(
            isInitialLoading: Boolean,
            initialError: Throwable?,
            products: List<OntoArticle>,
            cartInformation: CartInformation?,
            isRefreshLoading: Boolean,
            refreshError: Throwable?
        ): MaterialViewState = MaterialViewState(
            isInitialLoading = isInitialLoading,
            initialError = initialError,
            products = products,
            cartInformation = cartInformation,
            isRefreshLoading = isRefreshLoading,
            refreshError = refreshError
        )

        fun initialErrorState(
            error: Throwable,
            cartInformation: CartInformation?
        ): MaterialViewState = MaterialViewState(
            isInitialLoading = false,
            initialError = error,
            products = emptyList(),
            cartInformation = cartInformation,
            isRefreshLoading = false,
            refreshError = null
        )

        fun productsLoadedState(
            products: List<OntoArticle>,
            cartInformation: CartInformation?
        ): MaterialViewState = MaterialViewState(
            isInitialLoading = false,
            initialError = null,
            products = products,
            cartInformation = cartInformation,
            isRefreshLoading = false,
            refreshError = null
        )

        fun refreshLoadingState(
            products: List<OntoArticle>,
            cartInformation: CartInformation?
        ): MaterialViewState = MaterialViewState(
            isInitialLoading = false,
            initialError = null,
            products = products,
            cartInformation = cartInformation,
            isRefreshLoading = true,
            refreshError = null
        )

        fun refreshLoadingErrorState(
            products: List<OntoArticle>,
            cartInformation: CartInformation?,
            error: Throwable
        ): MaterialViewState = MaterialViewState(
            isInitialLoading = false,
            initialError = null,
            products = products,
            cartInformation = cartInformation,
            isRefreshLoading = false,
            refreshError = error
        )

    }
}