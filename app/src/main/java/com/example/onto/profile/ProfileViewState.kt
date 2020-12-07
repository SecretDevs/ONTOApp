package com.example.onto.profile

import com.example.onto.base.MviViewState
import com.example.onto.vo.inapp.CartInformation
import com.example.onto.vo.remote.OntoUser

data class ProfileViewState(
    val isInitialLoading: Boolean = false,
    val initialLoadingError: Throwable? = null,
    val user: OntoUser? = null,
    val cartInformation: CartInformation? = null
) : MviViewState {

    override fun log(): String = this.toString()

}