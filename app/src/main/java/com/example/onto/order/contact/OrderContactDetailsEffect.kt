package com.example.onto.order.contact

import com.example.onto.base.MviEffect

sealed class OrderContactDetailsEffect : MviEffect {

    data class PrefValuesLoadedEffect(
        val firstName: String,
        val lastName: String,
        val email: String,
        val phone: String
    ) : OrderContactDetailsEffect()

    object NoChangeEffect : OrderContactDetailsEffect()

}