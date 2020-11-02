package com.example.onto.product

import com.example.onto.base.MviIntent

sealed class ProductDetailsIntent : MviIntent {

    data class InitialIntent(
        val productId: Long
    ) : ProductDetailsIntent()

    data class ReloadIntent(
        val productId: Long
    ) : ProductDetailsIntent()

}