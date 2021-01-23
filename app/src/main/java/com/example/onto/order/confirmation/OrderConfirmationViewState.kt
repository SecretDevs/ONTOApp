package com.example.onto.order.confirmation

import com.example.onto.base.MviViewState
import com.example.onto.vo.inapp.CartItem

data class OrderConfirmationViewState(
    val orderNumber: Long,
    val items: List<CartItem>,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val deliveryType: Int,
    val deliveryAddress: List<String>,
    val commentary: String
) : MviViewState {
    override fun log(): String = this.toString()
}