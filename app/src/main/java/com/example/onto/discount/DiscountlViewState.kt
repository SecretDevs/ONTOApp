package com.example.onto.discount

import com.example.onto.base.MviViewState
import com.example.onto.vo.OntoOffer

data class DiscountlViewState(
    val isInitialLoading: Boolean,
    val initialError: Throwable?,
    val offers: List<OntoOffer>,
    val isRefreshLoading: Boolean,
    val refreshError: Throwable?
) : MviViewState {
    companion object {
        val initialState = DiscountlViewState(
            isInitialLoading = false,
            initialError = null,
            offers = emptyList(),
            isRefreshLoading = false,
            refreshError = null
        )

        val initialLoadingState = DiscountlViewState(
            isInitialLoading = true,
            initialError = null,
            offers = emptyList(),
            isRefreshLoading = false,
            refreshError = null
        )

        fun initialErrorState(error: Throwable): DiscountlViewState = DiscountlViewState(
            isInitialLoading = false,
            initialError = error,
            offers = emptyList(),
            isRefreshLoading = false,
            refreshError = null
        )

        fun productsLoadedState(offers: List<OntoOffer>): DiscountlViewState = DiscountlViewState(
            isInitialLoading = false,
            initialError = null,
            offers = offers,
            isRefreshLoading = false,
            refreshError = null
        )

        fun refreshLoadingState(offers: List<OntoOffer>): DiscountlViewState = DiscountlViewState(
            isInitialLoading = false,
            initialError = null,
            offers = offers,
            isRefreshLoading = true,
            refreshError = null
        )

        fun refreshLoadingErrorState(
            offers: List<OntoOffer>,
            error: Throwable
        ): DiscountlViewState = DiscountlViewState(
            isInitialLoading = false,
            initialError = null,
            offers = offers,
            isRefreshLoading = false,
            refreshError = error
        )

    }
}