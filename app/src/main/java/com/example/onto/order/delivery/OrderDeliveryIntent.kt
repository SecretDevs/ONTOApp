package com.example.onto.order.delivery

import com.example.onto.base.MviIntent
import com.example.onto.base.NothingIntent

sealed class OrderDeliveryIntent : MviIntent {

    object InitialIntent : OrderDeliveryIntent()

    data class SaveOrderDeliveryIntent(
        val deliveryType: Int,
        val city: String,
        val street: String,
        val house: String,
        val building: String,
        val apartment: String,
        val pickup: String,
        val commentary: String
    ) : OrderDeliveryIntent()

}