package com.example.onto.products

import com.example.onto.base.MviIntent

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

}