package com.example.onto.products

import com.example.onto.base.MviIntent
import com.example.onto.base.NothingIntent

sealed class ProductsIntent : MviIntent {

    object InitialIntent : ProductsIntent()

    object ReloadIntent : ProductsIntent()

    object RefreshIntent : ProductsIntent()

    data class SwitchTagIntent(
        val tagId: Long
    ) : ProductsIntent()

    data class AddToCartIntent(
        val productId: Long
    ) : ProductsIntent()

    object OpenCartIntent : ProductsIntent()

    data class OpenProductDetailsIntent(
        val productId: Long
    ) : ProductsIntent()

    object ProductsNothingIntent : ProductsIntent(), NothingIntent

}