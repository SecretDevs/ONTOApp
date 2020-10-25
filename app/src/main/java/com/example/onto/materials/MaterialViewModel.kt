package com.example.onto.materials

import androidx.hilt.lifecycle.ViewModelInject
import com.example.onto.base.BaseViewModel
import com.example.onto.utils.Result

class MaterialViewModel @ViewModelInject constructor(
    private val repository: MaterialsRepository)
    : BaseViewModel<MaterialViewState,MaterialEffect,MaterialIntent,MaterialAction>() {


    override fun initialState(): MaterialViewState = MaterialViewState.initialState

    override fun intentInterpreter(intent: MaterialIntent): MaterialAction =
        when (intent) {
            is MaterialIntent.InitialIntent -> MaterialAction.LoadProductsAction
            is MaterialIntent.ReloadIntent -> MaterialAction.LoadProductsAction
            is MaterialIntent.RefreshIntent -> MaterialAction.RefreshProductsAction

        }

    override suspend fun performAction(action: MaterialAction): MaterialEffect =
        when (action) {
            is MaterialAction.LoadProductsAction -> {
                addIntermediateEffect(MaterialEffect.InitialLoadingEffect)
                when (val result = repository.getMaterials()) {
                    is Result.Success -> MaterialEffect.MaterialLoadedEffect(result.data)
                    is Result.Error -> MaterialEffect.InitialLoadingErrorEffect(result.throwable)
                }
            }

            is MaterialAction.RefreshProductsAction -> {
                addIntermediateEffect(MaterialEffect.RefreshLoadingEffect)
                when (val result = repository.getMaterials()) {
                    is Result.Success -> MaterialEffect.MaterialLoadedEffect(result.data)
                    is Result.Error -> MaterialEffect.RefreshLoadingErrorEffect(result.throwable)
                }
            }

        }

    override fun stateReducer(
        oldState: MaterialViewState,
        effect: MaterialEffect
    ): MaterialViewState =
        when (effect) {
            is MaterialEffect.InitialLoadingEffect -> MaterialViewState.initialLoadingState
            is MaterialEffect.InitialLoadingErrorEffect -> MaterialViewState.initialErrorState(
                effect.throwable
            )
            is MaterialEffect.MaterialLoadedEffect -> MaterialViewState.productsLoadedState(effect.products)
            is MaterialEffect.RefreshLoadingEffect -> MaterialViewState.refreshLoadingState(oldState.products)
            is MaterialEffect.RefreshLoadingErrorEffect -> MaterialViewState.refreshLoadingErrorState(
                oldState.products,
                effect.throwable
            )
        }

}