package com.example.onto

import androidx.lifecycle.MutableLiveData
import com.example.onto.base.BaseViewModel

class ExampleViewModel :
    BaseViewModel<ExampleViewState, ExampleEffect, ExampleIntent, ExampleAction>() {
    private val viewStateViewModel = MutableLiveData<ExampleEffect>()
    private val exampleRepo = ExampleRepository()

    override val stateReducer: (ExampleViewState, ExampleEffect) -> ExampleViewState =
        { oldState, effect ->
            when (effect) {
                is ExampleEffect.UpdateCounter -> ExampleViewState(
                    firstCounter = effect.valueFirst,
                    secondCounter = effect.valueSecond
                )
            }
        }
    override val intentInterpreter: (ExampleIntent) -> ExampleAction = {
        when (it) {
            is ExampleIntent.AddOneFirstIntent -> ExampleAction.UpdateCounter(1, 1)
            is ExampleIntent.AddFiveFirstIntent -> ExampleAction.UpdateCounter(5, 1)
            is ExampleIntent.AddOneSecondIntent -> ExampleAction.UpdateCounter(1, 2)
            is ExampleIntent.AddFiveSecondIntent -> ExampleAction.UpdateCounter(5, 2)
            is ExampleIntent.ClearCountersIntent -> ExampleAction.ClearCounters
        }
    }

    init {
        _viewStateLiveData.value = ExampleViewState()
        _viewStateLiveData.addSource(viewStateViewModel) {
            _viewStateLiveData.value = stateReducer(_viewStateLiveData.value!!, it)
        }
    }

    override fun performAction(action: ExampleAction) {
        val effect = when (action) {
            is ExampleAction.UpdateCounter -> {
                exampleRepo.updateCounter(action.addNumber, action.addToCounter)
            }
            is ExampleAction.ClearCounters -> {
                exampleRepo.clearCounter()
            }
        }
        viewStateViewModel.value = effect
    }

}