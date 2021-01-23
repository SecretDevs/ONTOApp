package com.example.onto.order.contact

import com.example.onto.base.MviAction

sealed class OrderContactDetailsAction : MviAction {

    object LoadPrefValuesAction : OrderContactDetailsAction()

    data class SaveOrderContactDetailsAction(
        val firstName: String,
        val lastName: String,
        val email: String,
        val phone: String
    ) : OrderContactDetailsAction()

}