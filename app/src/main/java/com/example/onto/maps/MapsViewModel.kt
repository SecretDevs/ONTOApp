package com.example.onto.maps

import androidx.hilt.lifecycle.ViewModelInject
import com.example.onto.base.BaseViewModel
import com.example.onto.utils.Result

class MapsViewModel @ViewModelInject constructor(
    private val repository: MapsRepository
) : BaseViewModel<MapsViewState, MapsEffect, MapsIntent, MapsAction>() {

    override fun initialState(): MapsViewState = MapsViewState.initialState

    override fun intentInterpreter(intent: MapsIntent): MapsAction =
        when (intent) {
            MapsIntent.InitialIntent -> MapsAction.LoadShopsAction
            MapsIntent.ReloadIntent -> MapsAction.LoadShopsAction
            MapsIntent.MapLoadedIntent -> MapsAction.LoadedMapAction
            is MapsIntent.ShowShopInfoIntent -> MapsAction.SelectShopAction(intent.shop)
        }

    override suspend fun performAction(action: MapsAction): MapsEffect =
        when (action) {
            MapsAction.LoadShopsAction -> {
                addIntermediateEffect(MapsEffect.InitialLoadingEffect)
                when (val result = repository.getShops()) {
                    is Result.Success -> MapsEffect.ShopsLoadedEffect(result.data)
                    is Result.Error -> MapsEffect.InitialLoadingErrorEffect(result.throwable)
                }
            }
            MapsAction.LoadedMapAction -> MapsEffect.MapLoadedEffect
            is MapsAction.SelectShopAction -> MapsEffect.ShowShopInfoEffect(action.shop)
        }


    override fun stateReducer(oldState: MapsViewState, effect: MapsEffect): MapsViewState =
        when (effect) {
            MapsEffect.MapLoadedEffect -> MapsViewState.mapLoadedState(
                isInitialLoading = oldState.isInitialLoading,
                initialError = oldState.initialError,
                ontoShopsList = oldState.ontoShopsList,
                selectedShop = oldState.selectedShop
            )
            MapsEffect.InitialLoadingEffect -> MapsViewState.initialLoadingState(isMapLoading = oldState.isMapLoading)
            is MapsEffect.InitialLoadingErrorEffect -> MapsViewState.initialLoadingErrorState(
                isMapLoading = oldState.isMapLoading,
                initialError = effect.throwable
            )
            is MapsEffect.ShopsLoadedEffect -> MapsViewState.shopLoadedState(
                isMapLoading = oldState.isMapLoading,
                ontoShopsList = effect.shops
            )
            is MapsEffect.ShowShopInfoEffect -> MapsViewState.shopInfoState(
                isMapLoading = oldState.isMapLoading,
                ontoShopsList = oldState.ontoShopsList,
                selectedShop = oldState.ontoShopsList.firstOrNull {
                    effect.shop.first == it.location.latitude
                            && effect.shop.second == it.location.longitude
                }
            )
        }
}