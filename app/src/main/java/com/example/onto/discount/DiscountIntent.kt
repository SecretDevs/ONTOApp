package com.example.onto.discount

import com.example.onto.base.MviIntent
import com.example.onto.base.NothingIntent

sealed class DiscountIntent : MviIntent {

    object InitialIntent : DiscountIntent()

    object ReloadIntent : DiscountIntent()

    object RefreshIntent : DiscountIntent()

    data class AddProductToCartIntent(
        val productId: Long
    ) : DiscountIntent()

    data class NavigateToDiscountDetailsIntent(
        val discountId: Long
    ) : DiscountIntent()

    object NavigateToCartIntent : DiscountIntent()

    object DiscountNothingIntent : DiscountIntent(), NothingIntent

}