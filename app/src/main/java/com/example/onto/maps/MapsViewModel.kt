package com.example.onto.maps

import androidx.hilt.lifecycle.ViewModelInject
import com.example.onto.base.BaseViewModel
import com.example.onto.utils.Result

class MapsViewModel @ViewModelInject constructor(private val repository: MapsRepository) :
    BaseViewModel<MapsViewState, MapsEffect, MapsIntent, MapsAction>() {

    override fun initialState(): MapsViewState = MapsViewState.initialState

    override fun intentInterpreter(intent: MapsIntent): MapsAction =
        when (intent) {
            is MapsIntent.PermissionsCheckIntent -> MapsAction.CheckPermissionsAction
            is MapsIntent.InitialIntent -> MapsAction.LoadShopsAction
            is MapsIntent.ReloadIntent -> MapsAction.LoadShopsAction
            MapsIntent.MapsNothingIntent -> throw IllegalArgumentException("Nothing intent interpreting")
            MapsIntent.MapLoadedIntent -> MapsAction.LoadedMapAction
        }

    override suspend fun performAction(action: MapsAction): MapsEffect =
        when (action) {
            is MapsAction.CheckPermissionsAction -> {
                addIntermediateEffect(MapsEffect.InitialLoadingEffect)
                MapsEffect.PermissionsCheckEffect
            }
            is MapsAction.LoadShopsAction -> {
                addIntermediateEffect(MapsEffect.InitialLoadingEffect)
                when (val result = repository.getShops()) {
                    is Result.Success -> MapsEffect.ShopsLoadedEffect(result.data)
                    is Result.Error -> MapsEffect.InitialLoadingErrorEffect(result.throwable)
                }
            }
            MapsAction.LoadedMapAction -> MapsEffect.MapLoadedEffect
        }


    override fun stateReducer(oldState: MapsViewState, effect: MapsEffect): MapsViewState =
        when (effect) {
            is MapsEffect.PermissionsCheckEffect -> MapsViewState.permissionsCheckState
            is MapsEffect.InitialLoadingEffect -> MapsViewState.initialLoadingState
            is MapsEffect.InitialLoadingErrorEffect -> {
                MapsViewState.initialErrorState(
                    oldState.isMapLoaded,
                    effect.throwable
                )
            }
            is MapsEffect.ShopsLoadedEffect -> MapsViewState.shopsLoadedState(
                oldState.isMapLoaded,
                effect.shops
            )
            MapsEffect.MapLoadedEffect -> MapsViewState.mapLoaded(
                oldState.isInitialLoading,
                oldState.initialError,
                oldState.ontoShopsList
            )
        }
}