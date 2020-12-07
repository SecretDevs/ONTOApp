package com.example.onto.cart

import com.example.onto.base.MviViewState
import com.example.onto.vo.inapp.CartItem

data class CartViewState(
    val isInitialLoading: Boolean,
    val initialLoadingError: Throwable?,
    val data: List<CartItem>,
    val quantityChangeError: Throwable?
) : MviViewState {

    override fun log(): String = this.toString()

    companion object {
        val initialState = CartViewState(
            isInitialLoading = false,
            initialLoadingError = null,
            data = emptyList(),
            quantityChangeError = null
        )

        val loadingState = CartViewState(
            isInitialLoading = true,
            initialLoadingError = null,
            data = emptyList(),
            quantityChangeError = null
        )

        fun loadingErrorState(initialLoadingError: Throwable): CartViewState = CartViewState(
            isInitialLoading = false,
            initialLoadingError = initialLoadingError,
            data = emptyList(),
            quantityChangeError = null
        )

        fun cartLoadedState(data: List<CartItem>): CartViewState = CartViewState(
            isInitialLoading = false,
            initialLoadingError = null,
            data = data,
            quantityChangeError = null
        )

        fun quantityChangeError(
            data: List<CartItem>,
            error: Throwable
        ): CartViewState = CartViewState(
            isInitialLoading = false,
            initialLoadingError = null,
            data = data,
            quantityChangeError = error
        )

    }

}