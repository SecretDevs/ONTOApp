package com.example.onto.order.contact

import com.example.onto.base.MviViewState

data class OrderContactDetailsViewState(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String
) : MviViewState {
    override fun log(): String = this.toString()
}