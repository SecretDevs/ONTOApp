package com.example.onto.profile

import com.example.onto.base.MviIntent
import com.example.onto.base.NothingIntent

sealed class ProfileIntent : MviIntent {

    object InitialIntent : ProfileIntent()

    object ReloadIntent : ProfileIntent()

    object GoToQuestionIntent : ProfileIntent()

    object GoToCartIntent : ProfileIntent()

    data class SaveProfileIntent(
        val firstName: String,
        val lastName: String,
        val email: String,
        val phone: String,
        val useForDelivery: Boolean,
        val city: String,
        val street: String,
        val house: String,
        val building: String,
        val apartment: String
    ) : ProfileIntent()

}