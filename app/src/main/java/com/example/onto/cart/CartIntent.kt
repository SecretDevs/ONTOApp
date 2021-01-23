package com.example.onto.cart

import com.example.onto.base.MviIntent
import com.example.onto.base.NothingIntent

sealed class CartIntent : MviIntent {

    object InitialIntent : CartIntent()

    object ReloadIntent : CartIntent()

    object OpenShopIntent : CartIntent()

    object OpenOrderHistoryIntent : CartIntent()

    object GoBackIntent : CartIntent()

    data class RemoveProductIntent(
        val productId: Long
    ) : CartIntent()

    data class AddOneItemForProductIntent(
        val productId: Long
    ) : CartIntent()

    data class RemoveOneItemForProductIntent(
        val productId: Long
    ) : CartIntent()

    data class OpenProductDetailsIntent(val productId: Long) : CartIntent()

    object CartNothingIntent : CartIntent(), NothingIntent

    object CheckoutIntent : CartIntent()

}