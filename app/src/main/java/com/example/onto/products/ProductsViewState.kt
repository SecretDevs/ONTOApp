package com.example.onto.products

import com.example.onto.base.MviViewState
import com.example.onto.vo.OntoProduct

data class ProductsViewState(
    val isInitialLoading: Boolean,
    val initialError: Throwable?,
    val products: List<OntoProduct>,
    val isRefreshLoading: Boolean,
    val refreshError: Throwable?
) : MviViewState {
    companion object {
        val initialState = ProductsViewState(
            isInitialLoading = false,
            initialError = null,
            products = emptyList(),
            isRefreshLoading = false,
            refreshError = null
        )

        val initialLoadingState = ProductsViewState(
            isInitialLoading = true,
            initialError = null,
            products = emptyList(),
            isRefreshLoading = false,
            refreshError = null
        )

        fun initialErrorState(error: Throwable): ProductsViewState = ProductsViewState(
            isInitialLoading = false,
            initialError = error,
            products = emptyList(),
            isRefreshLoading = false,
            refreshError = null
        )

        fun productsLoadedState(products: List<OntoProduct>): ProductsViewState = ProductsViewState(
            isInitialLoading = false,
            initialError = null,
            products = products,
            isRefreshLoading = false,
            refreshError = null
        )

        fun refreshLoadingState(products: List<OntoProduct>): ProductsViewState = ProductsViewState(
            isInitialLoading = false,
            initialError = null,
            products = products,
            isRefreshLoading = true,
            refreshError = null
        )

        fun refreshLoadingErrorState(
            products: List<OntoProduct>,
            error: Throwable
        ): ProductsViewState = ProductsViewState(
            isInitialLoading = false,
            initialError = null,
            products = products,
            isRefreshLoading = false,
            refreshError = error
        )

    }
}