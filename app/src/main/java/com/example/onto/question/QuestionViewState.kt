package com.example.onto.question

import com.example.onto.base.MviViewState

data class QuestionViewState(
    val savedEmail: String? = null,
    val savedMessage: String? = null,
    val isLoading: Boolean = false,
    val isLoaded: Boolean? = null
) : MviViewState {
    override fun log(): String = toString()
}