package com.example.onto.profile

import com.example.onto.base.MviEffect
import com.example.onto.vo.inapp.CartInformation
import com.example.onto.vo.remote.OntoUser

sealed class ProfileEffect : MviEffect {

    object InitialLoadingEffect : ProfileEffect()

    data class InitialLoadingErrorEffect(
        val throwable: Throwable
    ) : ProfileEffect()

    data class UserLoadedEffect(
        val user: OntoUser,
        val forDelivery: Boolean
    ) : ProfileEffect()

    data class CartInformationLoaded(
        val cartInformation: CartInformation?
    ) : ProfileEffect()

    object NoEffect : ProfileEffect()

}