package com.example.onto

import com.example.onto.base.MviAction

sealed class ExampleAction : MviAction {

    object RetrieveValues : ExampleAction()

    data class UpdateFirstCounter(
        val addNumber: Int
    ) : ExampleAction()

    data class UpdateSecondCounter(
        val addNumber: Int
    ) : ExampleAction()

    object ClearCounters : ExampleAction()

}