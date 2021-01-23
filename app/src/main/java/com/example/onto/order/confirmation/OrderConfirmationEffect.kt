package com.example.onto.order.confirmation

import com.example.onto.base.MviEffect
import com.example.onto.vo.inapp.CartItem

sealed class OrderConfirmationEffect : MviEffect {

    data class OrderValuesLoaded(
        val orderNumber: Long,
        val items: List<CartItem>,
        val firstName: String,
        val lastName: String,
        val email: String,
        val phone: String,
        val deliveryType: Int,
        val deliveryAddress: List<String>,
        val commentary: String
    ) : OrderConfirmationEffect()

}