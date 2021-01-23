package com.example.onto.order.contact

import com.example.onto.base.MviIntent
import com.example.onto.base.NothingIntent

sealed class OrderContactDetailsIntent : MviIntent {

    object InitialIntent : OrderContactDetailsIntent()

    data class SaveOrderContactDetailsIntent(
        val firstName: String,
        val lastName: String,
        val email: String,
        val phone: String
    ) : OrderContactDetailsIntent()

}