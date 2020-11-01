package com.example.onto.discount

import com.example.onto.base.MviViewState
import com.example.onto.vo.OntoArticle

data class DiscountlViewState(
    val isInitialLoading: Boolean,
    val initialError: Throwable?,
    val products: List<OntoArticle>,
    val isRefreshLoading: Boolean,
    val refreshError: Throwable?
) : MviViewState {
    companion object {
        val initialState = DiscountlViewState(
            isInitialLoading = false,
            initialError = null,
            products = emptyList(),
            isRefreshLoading = false,
            refreshError = null
        )

        val initialLoadingState = DiscountlViewState(
            isInitialLoading = true,
            initialError = null,
            products = emptyList(),
            isRefreshLoading = false,
            refreshError = null
        )

        fun initialErrorState(error: Throwable): DiscountlViewState = DiscountlViewState(
            isInitialLoading = false,
            initialError = error,
            products = emptyList(),
            isRefreshLoading = false,
            refreshError = null
        )

        fun productsLoadedState(products: List<OntoArticle>): DiscountlViewState = DiscountlViewState(
            isInitialLoading = false,
            initialError = null,
            products = products,
            isRefreshLoading = false,
            refreshError = null
        )

        fun refreshLoadingState(products: List<OntoArticle>): DiscountlViewState = DiscountlViewState(
            isInitialLoading = false,
            initialError = null,
            products = products,
            isRefreshLoading = true,
            refreshError = null
        )

        fun refreshLoadingErrorState(
            products: List<OntoArticle>,
            error: Throwable
        ): DiscountlViewState = DiscountlViewState(
            isInitialLoading = false,
            initialError = null,
            products = products,
            isRefreshLoading = false,
            refreshError = error
        )

    }
}