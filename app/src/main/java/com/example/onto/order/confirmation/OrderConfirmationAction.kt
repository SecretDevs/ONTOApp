package com.example.onto.order.confirmation

import com.example.onto.base.MviAction

sealed class OrderConfirmationAction : MviAction {

    object LoadOrderValuesAction : OrderConfirmationAction()

}