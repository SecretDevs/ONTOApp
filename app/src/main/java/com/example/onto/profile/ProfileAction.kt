package com.example.onto.profile

import com.example.onto.base.MviAction

sealed class ProfileAction : MviAction {

    object LoadProfileAction : ProfileAction()

    object NavigateToQuestionAction : ProfileAction()

    object NavigateToCartAction : ProfileAction()

    data class SaveProfileAction(
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
    ) : ProfileAction()

}