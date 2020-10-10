package com.example.onto

import com.example.onto.base.MviAction

sealed class ExampleAction : MviAction {

    data class UpdateCounter(
        val addNumber: Int,
        val addToCounter: Int
    ) : ExampleAction()

    object ClearCounters : ExampleAction()

}