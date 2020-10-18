package com.example.onto.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.onto.utils.distinctUnilChanged
import com.example.onto.utils.map

abstract class BaseViewModel<
        VS : MviViewState,
        E : MviEffect,
        I : MviIntent,
        A : MviAction> : ViewModel(), MviViewModel<VS, I> {
    protected abstract fun initialState(): VS
    protected abstract fun intentInterpreter(intent: I): A
    protected abstract fun performAction(action: A): E
    protected abstract fun stateReducer(oldState: VS, effect: E): VS

    private val viewStateLiveData = MediatorLiveData<VS>().also {
        it.value = initialState()
    }

    private val _effectLiveData = MediatorLiveData<E>().also { effectLiveData ->
        viewStateLiveData.addSource(effectLiveData) {
            viewStateLiveData.value = stateReducer(viewStateLiveData.value!!, it)
        }
    }

    private var clearLastEffectSource: (() -> Unit)? = null

    protected fun addIntermediateEffect(effect: E) {
        _effectLiveData.value = effect
    }

    override fun states(): LiveData<VS> = viewStateLiveData.distinctUnilChanged()

    override fun processIntents(intents: LiveData<I>) {
        clearLastEffectSource?.let { it() }

        val effectLiveData = intents.map { performAction(intentInterpreter(it)) }
        _effectLiveData.addSource(effectLiveData) {
            _effectLiveData.value = it
        }

        clearLastEffectSource = { _effectLiveData.removeSource(effectLiveData) }
    }

    override fun onCleared() {
        super.onCleared()
        clearLastEffectSource?.let { it() }
    }
}