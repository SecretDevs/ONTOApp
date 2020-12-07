package com.example.onto.products

import com.example.onto.base.MviAction

sealed class ProductsAction : MviAction {

    object LoadProductsAction : ProductsAction()

    object RefreshProductsAction : ProductsAction()

    data class SwitchFilterTagAction(
        val tagId: Long
    ) : ProductsAction()

    data class AddToCartAction(
        val productId: Long
    ) : ProductsAction()

    object NavigateToCartAction : ProductsAction()

    data class NavigateToProductDetailsAction(
        val productId: Long
    ) : ProductsAction()

}