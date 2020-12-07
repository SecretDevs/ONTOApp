package com.example.onto.discount

import com.example.onto.base.MviViewState
import com.example.onto.vo.inapp.CartInformation
import com.example.onto.vo.remote.OntoOffer

data class DiscountViewState(
    val isInitialLoading: Boolean,
    val initialError: Throwable?,
    val offers: List<OntoOffer>,
    val cartInformation: CartInformation?,
    val isRefreshLoading: Boolean,
    val refreshError: Throwable?
) : MviViewState {

    override fun log(): String = this.toString()

    companion object {
        val initialState = DiscountViewState(
            isInitialLoading = false,
            initialError = null,
            offers = emptyList(),
            cartInformation = null,
            isRefreshLoading = false,
            refreshError = null
        )

        val initialLoadingState = DiscountViewState(
            isInitialLoading = true,
            initialError = null,
            offers = emptyList(),
            cartInformation = null,
            isRefreshLoading = false,
            refreshError = null
        )

        fun cartInformationLoaded(
            isInitialLoading: Boolean,
            initialError: Throwable?,
            offers: List<OntoOffer>,
            cartInformation: CartInformation?,
            isRefreshLoading: Boolean,
            refreshError: Throwable?
        ): DiscountViewState = DiscountViewState(
            isInitialLoading = isInitialLoading,
            initialError = initialError,
            offers = offers,
            cartInformation = cartInformation,
            isRefreshLoading = isRefreshLoading,
            refreshError = refreshError
        )

        fun initialErrorState(
            error: Throwable,
            cartInformation: CartInformation?
        ): DiscountViewState = DiscountViewState(
            isInitialLoading = false,
            initialError = error,
            offers = emptyList(),
            cartInformation = cartInformation,
            isRefreshLoading = false,
            refreshError = null
        )

        fun productsLoadedState(
            offers: List<OntoOffer>,
            cartInformation: CartInformation?
        ): DiscountViewState = DiscountViewState(
            isInitialLoading = false,
            initialError = null,
            offers = offers,
            cartInformation = cartInformation,
            isRefreshLoading = false,
            refreshError = null
        )

        fun refreshLoadingState(
            offers: List<OntoOffer>,
            cartInformation: CartInformation?
        ): DiscountViewState = DiscountViewState(
            isInitialLoading = false,
            initialError = null,
            offers = offers,
            cartInformation = cartInformation,
            isRefreshLoading = true,
            refreshError = null
        )

        fun refreshLoadingErrorState(
            offers: List<OntoOffer>,
            error: Throwable,
            cartInformation: CartInformation?
        ): DiscountViewState = DiscountViewState(
            isInitialLoading = false,
            initialError = null,
            offers = offers,
            cartInformation = cartInformation,
            isRefreshLoading = false,
            refreshError = error
        )

    }
}