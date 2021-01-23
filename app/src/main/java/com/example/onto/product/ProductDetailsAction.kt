package com.example.onto.product

import com.example.onto.base.MviAction

sealed class ProductDetailsAction : MviAction {

    data class LoadProductDetailsAction(
        val productId: Long
    ) : ProductDetailsAction()

    data class AddToCartAction(
        val productId: Long,
        val isSimilar: Boolean
    ) : ProductDetailsAction()

    data class ChangeQuantityAction(
        val change: Int
    ) : ProductDetailsAction()

    object NavigateBackAction : ProductDetailsAction()

    object NavigateToCartAction : ProductDetailsAction()

    object UpdateCartAction : ProductDetailsAction()

}