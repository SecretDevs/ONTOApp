package com.example.onto.product

import com.example.onto.base.MviIntent
import com.example.onto.base.NothingIntent

sealed class ProductDetailsIntent : MviIntent {

    data class InitialIntent(
        val productId: Long
    ) : ProductDetailsIntent()

    data class ReloadIntent(
        val productId: Long
    ) : ProductDetailsIntent()

    data class AddToCartIntent(
        val productId: Long
    ) : ProductDetailsIntent()

    data class AddSimilarToCartIntent(
        val productId: Long
    ) : ProductDetailsIntent()

    object AddOneIntent : ProductDetailsIntent()

    object RemoveOneIntent : ProductDetailsIntent()

    object NavigateBackIntent : ProductDetailsIntent()

    object NavigateToCartIntent : ProductDetailsIntent()

    object UpdateCartIntent : ProductDetailsIntent()

}