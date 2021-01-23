package com.example.onto.maps

import androidx.hilt.lifecycle.ViewModelInject
import com.example.onto.base.BaseViewModel
import com.example.onto.data.usecase.CartUseCase
import com.example.onto.navigation.Coordinator
import com.example.onto.utils.Result

class MapsViewModel @ViewModelInject constructor(
    private val coordinator: Coordinator,
    private val cartUseCase: CartUseCase,
    private val repository: MapsRepository
) : BaseViewModel<MapsViewState, MapsEffect, MapsIntent, MapsAction>() {

    override fun initialState(): MapsViewState = MapsViewState.initialState(null)

    override fun intentInterpreter(intent: MapsIntent): MapsAction =
        when (intent) {
            is MapsIntent.PermissionsCheckIntent -> MapsAction.CheckPermissionsAction
            is MapsIntent.InitialIntent -> MapsAction.LoadShopsAction
            is MapsIntent.ReloadIntent -> MapsAction.LoadShopsAction
            MapsIntent.MapLoadedIntent -> MapsAction.LoadedMapAction
            MapsIntent.OpenCartIntent -> MapsAction.NavigateToCartAction
            MapsIntent.MapsUpdateCart -> MapsAction.UpdateCartAction
        }

    override suspend fun performAction(action: MapsAction): MapsEffect =
        when (action) {
            is MapsAction.CheckPermissionsAction -> {
                addIntermediateEffect(MapsEffect.InitialLoadingEffect)
                MapsEffect.PermissionsCheckEffect
            }
            is MapsAction.LoadShopsAction -> {
                addIntermediateEffect(MapsEffect.InitialLoadingEffect)
                addIntermediateEffect(
                    MapsEffect.CartInformationLoadedEffect(
                        when (val result = cartUseCase.getCartInformation()) {
                            is Result.Success -> result.data
                            is Result.Error -> null
                        }
                    )
                )
                when (val result = repository.getShops()) {
                    is Result.Success -> MapsEffect.ShopsLoadedEffect(result.data)
                    is Result.Error -> MapsEffect.InitialLoadingErrorEffect(result.throwable)
                }
            }
            MapsAction.LoadedMapAction -> MapsEffect.MapLoadedEffect
            MapsAction.NavigateToCartAction -> {
                coordinator.navigateToCart()
                MapsEffect.NoEffect
            }
            MapsAction.UpdateCartAction -> MapsEffect.CartInformationLoadedEffect(
                when (val result = cartUseCase.getCartInformation()) {
                    is Result.Success -> result.data
                    is Result.Error -> null
                }
            )
        }


    override fun stateReducer(oldState: MapsViewState, effect: MapsEffect): MapsViewState =
        when (effect) {
            is MapsEffect.PermissionsCheckEffect -> MapsViewState.permissionsCheckState
            is MapsEffect.InitialLoadingEffect -> MapsViewState.initialLoadingState(null)
            is MapsEffect.InitialLoadingErrorEffect -> MapsViewState.initialErrorState(
                oldState.isMapLoaded,
                effect.throwable,
                oldState.cartInformation
            )
            is MapsEffect.ShopsLoadedEffect -> MapsViewState.shopsLoadedState(
                oldState.isMapLoaded,
                effect.shops,
                oldState.cartInformation
            )
            MapsEffect.MapLoadedEffect -> MapsViewState.mapLoaded(
                oldState.cartInformation,
                oldState.isInitialLoading,
                oldState.initialError,
                oldState.ontoShopsList
            )
            is MapsEffect.CartInformationLoadedEffect -> MapsViewState.cartInformationLoaded(
                effect.cartInformation,
                oldState.isMapLoaded,
                oldState.isPermissionsChecked,
                oldState.isInitialLoading,
                oldState.initialError,
                oldState.ontoShopsList
            )
            MapsEffect.NoEffect -> oldState
        }
}