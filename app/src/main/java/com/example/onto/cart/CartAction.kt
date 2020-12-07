package com.example.onto.cart

import com.example.onto.base.MviAction

sealed class CartAction : MviAction {

    object LoadCartAction : CartAction()

    object OpenShopAction: CartAction()

    object OpenOrderHistoryAction : CartAction()

    object GoBackAction : CartAction()

    data class ChangeItemCountAction(
        val productId: Long,
        val change: Int
    ) : CartAction()

}