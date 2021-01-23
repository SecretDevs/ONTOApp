package com.example.onto.order.delivery

import com.example.onto.base.MviAction

sealed class OrderDeliveryAction : MviAction {

    object LoadOrderAction : OrderDeliveryAction()

    data class SaveOrderDeliveryAction(
        val deliveryType: Int,
        val city: String,
        val street: String,
        val house: String,
        val building: String,
        val apartment: String,
        val pickup: String,
        val commentary: String
    ) : OrderDeliveryAction()

}