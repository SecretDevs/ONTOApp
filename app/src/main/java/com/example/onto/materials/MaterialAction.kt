package com.example.onto.materials

import com.example.onto.base.MviAction

sealed class MaterialAction : MviAction {

    object LoadProductsAction : MaterialAction()

    object RefreshProductsAction : MaterialAction()

    object NavigateToCartAction : MaterialAction()

    data class NavigateToProductAction(
        val productId: Long
    ) : MaterialAction()

}