package com.example.onto.products

import androidx.hilt.lifecycle.ViewModelInject
import com.example.onto.base.BaseViewModel
import com.example.onto.utils.Result

class ProductsViewModel @ViewModelInject constructor(
    private val productsUseCase: ProductsUseCase
) : BaseViewModel<ProductsViewState, ProductsEffect, ProductsIntent, ProductsAction>() {
    override fun initialState(): ProductsViewState = ProductsViewState.initialState

    override fun intentInterpreter(intent: ProductsIntent): ProductsAction =
        when (intent) {
            is ProductsIntent.InitialIntent -> ProductsAction.LoadProductsAction
            is ProductsIntent.ReloadIntent -> ProductsAction.LoadProductsAction
            is ProductsIntent.RefreshIntent -> ProductsAction.RefreshProductsAction
            is ProductsIntent.SwitchTagIntent -> ProductsAction.SwitchFilterTagAction(intent.tagId)
            is ProductsIntent.AddToCartIntent -> ProductsAction.AddToCartAction(intent.productId)
        }

    override suspend fun performAction(action: ProductsAction): ProductsEffect =
        when (action) {
            is ProductsAction.LoadProductsAction -> {
                addIntermediateEffect(ProductsEffect.InitialLoadingEffect)
                when (val result = productsUseCase.getProducts(emptyList())) {
                    is Result.Success -> ProductsEffect.ProductsLoadedEffect(result.data)
                    is Result.Error -> ProductsEffect.InitialLoadingErrorEffect(result.throwable)
                }
            }
            is ProductsAction.RefreshProductsAction -> {
                addIntermediateEffect(ProductsEffect.RefreshLoadingEffect)
                when (val result = productsUseCase.getProducts(emptyList())) {
                    is Result.Success -> ProductsEffect.ProductsLoadedEffect(result.data)
                    is Result.Error -> ProductsEffect.RefreshLoadingErrorEffect(result.throwable)
                }
            }
            is ProductsAction.SwitchFilterTagAction -> {
                throw UnsupportedOperationException("API is not realized yet")
            }
            is ProductsAction.AddToCartAction -> {
                throw UnsupportedOperationException("API is not realized yet")
            }
        }

    override fun stateReducer(
        oldState: ProductsViewState,
        effect: ProductsEffect
    ): ProductsViewState =
        when (effect) {
            is ProductsEffect.InitialLoadingEffect -> ProductsViewState.initialLoadingState
            is ProductsEffect.InitialLoadingErrorEffect -> ProductsViewState.initialErrorState(
                effect.throwable
            )
            is ProductsEffect.ProductsLoadedEffect -> ProductsViewState.productsLoadedState(effect.products)
            is ProductsEffect.RefreshLoadingEffect -> ProductsViewState.refreshLoadingState(oldState.products)
            is ProductsEffect.RefreshLoadingErrorEffect -> ProductsViewState.refreshLoadingErrorState(
                oldState.products,
                effect.throwable
            )
        }
}