package com.example.onto.profile

import com.example.onto.base.MviEffect
import com.example.onto.vo.OntoUser

sealed class ProfileEffect : MviEffect {

    object InitialLoadingEffect : ProfileEffect()

    data class InitialLoadingErrorEffect(
        val throwable: Throwable
    ) : ProfileEffect()

    data class UserLoadedEffect(
        val user: OntoUser
    ) : ProfileEffect()

}