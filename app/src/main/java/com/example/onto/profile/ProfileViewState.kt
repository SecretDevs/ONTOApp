package com.example.onto.profile

import com.example.onto.base.MviViewState
import com.example.onto.vo.OntoUser

data class ProfileViewState(
    val isInitialLoading: Boolean = false,
    val initialLoadingError: Throwable? = null,
    val user: OntoUser? = null
) : MviViewState