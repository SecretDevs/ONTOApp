package com.example.onto.order.confirmation

import com.example.onto.base.MviIntent
import com.example.onto.base.NothingIntent

sealed class OrderConfirmationIntent : MviIntent {

    object InitialIntent : OrderConfirmationIntent()

    object OrderConfirmationNothingIntent : OrderConfirmationIntent(), NothingIntent

}