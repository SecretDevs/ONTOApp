package com.example.onto.discount
import com.example.onto.base.MviAction

sealed class DiscountAction : MviAction {

    object LoadProductsAction : DiscountAction()

    object RefreshProductsAction : DiscountAction()

    data class AddProductToCart(
        val productId: Long
    ) : DiscountAction()

    object NavigateToCartAction : DiscountAction()

    data class NavigateToProductAction(
        val productId: Long
    ) : DiscountAction()

}