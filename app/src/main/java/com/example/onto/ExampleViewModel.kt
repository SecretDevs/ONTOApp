package com.example.onto

import com.example.onto.base.BaseViewModel

class ExampleViewModel :
    BaseViewModel<ExampleViewState, ExampleEffect, ExampleIntent, ExampleAction>() {
    private val exampleRepo = ExampleRepository()

    override fun initialState(): ExampleViewState = ExampleViewState()

    override fun intentInterpreter(intent: ExampleIntent): ExampleAction =
        when (intent) {
            is ExampleIntent.InitialIntent -> ExampleAction.RetrieveValues
            is ExampleIntent.AddOneFirstIntent -> ExampleAction.UpdateFirstCounter(1)
            is ExampleIntent.AddFiveFirstIntent -> ExampleAction.UpdateFirstCounter(5)
            is ExampleIntent.AddOneSecondIntent -> ExampleAction.UpdateSecondCounter(1)
            is ExampleIntent.AddFiveSecondIntent -> ExampleAction.UpdateSecondCounter(5)
            is ExampleIntent.ClearCountersIntent -> ExampleAction.ClearCounters
        }

    override suspend fun performAction(action: ExampleAction): ExampleEffect =
        when (action) {
            is ExampleAction.RetrieveValues -> {
                val (first, second) = exampleRepo.getValues()
                ExampleEffect.UpdateCounter(first, second)
            }
            is ExampleAction.UpdateFirstCounter -> {
                val (first, second) = exampleRepo.updateCounter(action.addNumber, 1)
                ExampleEffect.UpdateCounter(first, second)
            }
            is ExampleAction.UpdateSecondCounter -> {
                val (first, second) = exampleRepo.updateCounter(action.addNumber, 2)
                ExampleEffect.UpdateCounter(first, second)
            }
            is ExampleAction.ClearCounters -> {
                val (first, second) = exampleRepo.clearCounter()
                ExampleEffect.UpdateCounter(first, second)
            }
        }

    override fun stateReducer(oldState: ExampleViewState, effect: ExampleEffect): ExampleViewState =
        when (effect) {
            is ExampleEffect.UpdateCounter -> ExampleViewState(
                firstCounter = effect.valueFirst,
                secondCounter = effect.valueSecond
            )
        }

}