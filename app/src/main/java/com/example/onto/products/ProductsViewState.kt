package com.example.onto.products

import com.example.onto.base.MviViewState
import com.example.onto.vo.inapp.CartInformation
import com.example.onto.vo.inapp.OntoProduct

data class ProductsViewState(
    val isInitialLoading: Boolean,
    val initialError: Throwable?,
    val products: List<OntoProduct>,
    val cartInfo: CartInformation?,
    val isRefreshLoading: Boolean,
    val refreshError: Throwable?
) : MviViewState {

    override fun log(): String = this.toString()

    companion object {
        val initialState = ProductsViewState(
            isInitialLoading = false,
            initialError = null,
            products = emptyList(),
            cartInfo = null,
            isRefreshLoading = false,
            refreshError = null
        )

        val initialLoadingState = ProductsViewState(
            isInitialLoading = true,
            initialError = null,
            products = emptyList(),
            cartInfo = null,
            isRefreshLoading = false,
            refreshError = null
        )

        fun cartInfoLoaded(
            isInitialLoading: Boolean,
            initialError: Throwable?,
            products: List<OntoProduct>,
            cartInfo: CartInformation?,
            isRefreshLoading: Boolean,
            refreshError: Throwable?
        ): ProductsViewState =
            ProductsViewState(
                isInitialLoading = isInitialLoading,
                initialError = initialError,
                products = products,
                cartInfo = cartInfo,
                isRefreshLoading = isRefreshLoading,
                refreshError = refreshError
            )

        fun initialErrorState(
            error: Throwable,
            cartInfo: CartInformation?
        ): ProductsViewState =
            ProductsViewState(
                isInitialLoading = false,
                initialError = error,
                products = emptyList(),
                cartInfo = cartInfo,
                isRefreshLoading = false,
                refreshError = null
            )

        fun productsLoadedState(
            products: List<OntoProduct>,
            cartInfo: CartInformation?
        ): ProductsViewState =
            ProductsViewState(
                isInitialLoading = false,
                initialError = null,
                products = products,
                cartInfo = cartInfo,
                isRefreshLoading = false,
                refreshError = null
            )

        fun refreshLoadingState(
            products: List<OntoProduct>,
            cartInfo: CartInformation?
        ): ProductsViewState =
            ProductsViewState(
                isInitialLoading = false,
                initialError = null,
                products = products,
                cartInfo = cartInfo,
                isRefreshLoading = true,
                refreshError = null
            )

        fun refreshLoadingErrorState(
            products: List<OntoProduct>,
            error: Throwable,
            cartInfo: CartInformation?
        ): ProductsViewState = ProductsViewState(
            isInitialLoading = false,
            initialError = null,
            products = products,
            cartInfo = cartInfo,
            isRefreshLoading = false,
            refreshError = error
        )

    }
}