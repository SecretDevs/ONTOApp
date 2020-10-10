package com.example.onto.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<
        VS : MviViewState,
        E : MviEffect,
        I : MviIntent,
        A : MviAction> : ViewModel(), MviViewModel<VS, I> {
    protected abstract val stateReducer: (VS, E) -> VS
    protected abstract val intentInterpreter: (I) -> A

    protected abstract fun performAction(action: A)

    protected val _viewStateLiveData = MediatorLiveData<VS>()
    protected var clearObserver: (() -> Unit)? = null

    override fun states(): LiveData<VS> = _viewStateLiveData

    override fun processIntents(intents: LiveData<I>) {
        clearObserver?.let { it() }

        val intentObserver = Observer<I> {
            performAction(intentInterpreter(it))
        }
        intents.observeForever(intentObserver)

        clearObserver = { intents.removeObserver(intentObserver) }
    }

    override fun onCleared() {
        super.onCleared()
        clearObserver?.let { it() }
    }
}