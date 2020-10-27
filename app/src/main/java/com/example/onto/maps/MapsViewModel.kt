package com.example.onto.maps

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import com.example.onto.base.BaseViewModel
import com.example.onto.maps.MapsViewState.Companion.shopsLoadedState
import com.example.onto.vo.OntoArticle
import com.example.onto.vo.OntoShop
import com.example.onto.utils.Result

class MapsViewModel @ViewModelInject constructor(private val repository: MapsRepository) :
    BaseViewModel<MapsViewState, MapsEffect, MapsIntent, MapsAction>() {

    override fun initialState(): MapsViewState = MapsViewState.initialState

    override fun intentInterpreter(intent: MapsIntent): MapsAction =
        when (intent) {
            is MapsIntent.InitialIntent -> MapsAction.LoadShopsAction
            is MapsIntent.ReloadIntent -> MapsAction.LoadShopsAction
        }

    override suspend fun performAction(action: MapsAction): MapsEffect =
        when (action) {
            is MapsAction.LoadShopsAction -> {
                addIntermediateEffect(MapsEffect.InitialLoadingEffect)
                when (val result = repository.getShops()){
                    is Result.Success -> MapsEffect.ShopsLoadedEffect(result.data)
                    is Result.Error -> MapsEffect.InitialLoadingErrorEffect(result.throwable)
                }
            }
        }


    override fun stateReducer(oldState: MapsViewState, effect: MapsEffect): MapsViewState =
        when (effect) {
            is MapsEffect.InitialLoadingEffect -> MapsViewState.initialLoadingState
            is MapsEffect.InitialLoadingErrorEffect -> MapsViewState.initialErrorState(
                effect.throwable
            )
            is MapsEffect.ShopsLoadedEffect -> MapsViewState.shopsLoadedState(effect.shops)
        }
}