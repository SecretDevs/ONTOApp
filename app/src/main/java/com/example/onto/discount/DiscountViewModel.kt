package com.example.onto.discount

import androidx.hilt.lifecycle.ViewModelInject
import com.example.onto.base.BaseViewModel
import com.example.onto.utils.Result

class DiscountViewModel @ViewModelInject constructor(
    private val repository: DiscountRepository)
    : BaseViewModel<DiscountlViewState, DiscountEffect, DiscountIntent, DiscountAction>() {


    override fun initialState(): DiscountlViewState = DiscountlViewState.initialState

    override fun intentInterpreter(intent: DiscountIntent): DiscountAction =
        when (intent) {
            is DiscountIntent.InitialIntent -> DiscountAction.LoadProductsAction
            is DiscountIntent.ReloadIntent -> DiscountAction.LoadProductsAction
            is DiscountIntent.RefreshIntent -> DiscountAction.RefreshProductsAction

        }

    override suspend fun performAction(action: DiscountAction): DiscountEffect =
        when (action) {
            is DiscountAction.LoadProductsAction -> {
                addIntermediateEffect(DiscountEffect.InitialLoadingEffect)
                when (val result = repository.getStocks()) {
                    is Result.Success -> DiscountEffect.DiscountLoadedEffect(result.data)
                    is Result.Error -> DiscountEffect.InitialLoadingErrorEffect(result.throwable)
                }
            }

            is DiscountAction.RefreshProductsAction -> {
                addIntermediateEffect(DiscountEffect.RefreshLoadingEffect)
                when (val result = repository.getStocks()) {
                    is Result.Success -> DiscountEffect.DiscountLoadedEffect(result.data)
                    is Result.Error -> DiscountEffect.RefreshLoadingErrorEffect(result.throwable)
                }
            }

        }

    override fun stateReducer(
        oldState: DiscountlViewState,
        effect: DiscountEffect
    ): DiscountlViewState =
        when (effect) {
            is DiscountEffect.InitialLoadingEffect -> DiscountlViewState.initialLoadingState
            is DiscountEffect.InitialLoadingErrorEffect -> DiscountlViewState.initialErrorState(
                effect.throwable
            )
            is DiscountEffect.DiscountLoadedEffect -> DiscountlViewState.productsLoadedState(effect.products)
            is DiscountEffect.RefreshLoadingEffect -> DiscountlViewState.refreshLoadingState(oldState.products)
            is DiscountEffect.RefreshLoadingErrorEffect -> DiscountlViewState.refreshLoadingErrorState(
                oldState.products,
                effect.throwable
            )
        }

}