package com.example.onto.product

import androidx.hilt.lifecycle.ViewModelInject
import com.example.onto.base.BaseViewModel
import com.example.onto.utils.Result

class ProductDetailsViewModel @ViewModelInject constructor(
    private val productsUseCase: ProductDetailsUseCase
) : BaseViewModel<ProductDetailsViewState, ProductDetailsEffect, ProductDetailsIntent, ProductDetailsAction>() {
    override fun initialState(): ProductDetailsViewState = ProductDetailsViewState()

    override fun intentInterpreter(intent: ProductDetailsIntent): ProductDetailsAction =
        when (intent) {
            is ProductDetailsIntent.InitialIntent -> ProductDetailsAction.LoadProductDetailsAction(
                intent.productId
            )
            is ProductDetailsIntent.ReloadIntent -> ProductDetailsAction.LoadProductDetailsAction(
                intent.productId
            )
        }

    override suspend fun performAction(action: ProductDetailsAction): ProductDetailsEffect =
        when (action) {
            is ProductDetailsAction.LoadProductDetailsAction -> {
                addIntermediateEffect(ProductDetailsEffect.InitialLoadingEffect)
                when (val result = productsUseCase.getProductDetails(action.productId)) {
                    is Result.Success -> ProductDetailsEffect.ProductDetailsLoadedEffect(result.data)
                    is Result.Error -> ProductDetailsEffect.InitialLoadingErrorEffect(result.throwable)
                }
            }
        }

    override fun stateReducer(
        oldState: ProductDetailsViewState,
        effect: ProductDetailsEffect
    ): ProductDetailsViewState =
        when (effect) {
            ProductDetailsEffect.InitialLoadingEffect -> ProductDetailsViewState(
                isInitialLoading = true,
                initialLoadingError = null,
                product = null
            )
            is ProductDetailsEffect.InitialLoadingErrorEffect -> ProductDetailsViewState(
                isInitialLoading = false,
                initialLoadingError = effect.throwable,
                product = null
            )
            is ProductDetailsEffect.ProductDetailsLoadedEffect -> ProductDetailsViewState(
                isInitialLoading = false,
                initialLoadingError = null,
                product = effect.product
            )
        }

}