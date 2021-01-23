package com.example.onto.product

import androidx.hilt.lifecycle.ViewModelInject
import com.example.onto.base.BaseViewModel
import com.example.onto.data.usecase.CartUseCase
import com.example.onto.data.usecase.ProductDetailsUseCase
import com.example.onto.navigation.Coordinator
import com.example.onto.utils.Result

class ProductDetailsViewModel @ViewModelInject constructor(
    private val productsUseCase: ProductDetailsUseCase,
    private val cartUseCase: CartUseCase,
    private val coordinator: Coordinator
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
            ProductDetailsIntent.NavigateBackIntent -> ProductDetailsAction.NavigateBackAction
            ProductDetailsIntent.NavigateToCartIntent -> ProductDetailsAction.NavigateToCartAction
            is ProductDetailsIntent.AddToCartIntent -> ProductDetailsAction.AddToCartAction(
                intent.productId,
                false
            )
            ProductDetailsIntent.AddOneIntent -> ProductDetailsAction.ChangeQuantityAction(1)
            ProductDetailsIntent.RemoveOneIntent -> ProductDetailsAction.ChangeQuantityAction(-1)
            is ProductDetailsIntent.AddSimilarToCartIntent -> ProductDetailsAction.AddToCartAction(
                intent.productId,
                true
            )
            ProductDetailsIntent.UpdateCartIntent -> ProductDetailsAction.UpdateCartAction
        }

    override suspend fun performAction(action: ProductDetailsAction): ProductDetailsEffect =
        when (action) {
            is ProductDetailsAction.LoadProductDetailsAction -> {
                addIntermediateEffect(ProductDetailsEffect.InitialLoadingEffect)
                addIntermediateEffect(
                    ProductDetailsEffect.CartInformationLoadedEffect(
                        when (val result = cartUseCase.getCartInformation()) {
                            is Result.Success -> result.data
                            is Result.Error -> null
                        }
                    )
                )
                when (val result = productsUseCase.getProductDetails(action.productId)) {
                    is Result.Success -> ProductDetailsEffect.ProductDetailsLoadedEffect(result.data)
                    is Result.Error -> ProductDetailsEffect.InitialLoadingErrorEffect(result.throwable)
                }
            }
            ProductDetailsAction.NavigateBackAction -> {
                coordinator.pop()
                ProductDetailsEffect.NoEffect
            }
            ProductDetailsAction.NavigateToCartAction -> {
                coordinator.navigateToCart()
                ProductDetailsEffect.NoEffect
            }
            is ProductDetailsAction.AddToCartAction -> {
                cartUseCase.addCartItem(
                    action.productId,
                    if (!action.isSimilar) viewStateLiveData.value!!.quantity else 1
                )
                ProductDetailsEffect.CartInformationLoadedEffect(
                    when (val result = cartUseCase.getCartInformation()) {
                        is Result.Success -> result.data
                        is Result.Error -> null
                    }
                )
            }
            is ProductDetailsAction.ChangeQuantityAction -> ProductDetailsEffect.ChangeQuantityEffect(
                action.change
            )
            ProductDetailsAction.UpdateCartAction -> ProductDetailsEffect.CartInformationLoadedEffect(
                when (val result = cartUseCase.getCartInformation()) {
                    is Result.Success -> result.data
                    is Result.Error -> null
                }
            )
        }

    override fun stateReducer(
        oldState: ProductDetailsViewState,
        effect: ProductDetailsEffect
    ): ProductDetailsViewState =
        when (effect) {
            ProductDetailsEffect.InitialLoadingEffect -> ProductDetailsViewState(
                isInitialLoading = true,
                initialLoadingError = null,
                product = null,
                quantity = 0,
                cartInformation = null
            )
            is ProductDetailsEffect.InitialLoadingErrorEffect -> ProductDetailsViewState(
                isInitialLoading = false,
                initialLoadingError = effect.throwable,
                product = null,
                quantity = 0,
                cartInformation = oldState.cartInformation
            )
            is ProductDetailsEffect.ProductDetailsLoadedEffect -> ProductDetailsViewState(
                isInitialLoading = false,
                initialLoadingError = null,
                product = effect.product,
                quantity = 1,
                cartInformation = oldState.cartInformation
            )
            is ProductDetailsEffect.CartInformationLoadedEffect -> ProductDetailsViewState(
                isInitialLoading = oldState.isInitialLoading,
                initialLoadingError = oldState.initialLoadingError,
                product = oldState.product,
                quantity = oldState.quantity,
                cartInformation = effect.cartInformation
            )
            ProductDetailsEffect.NoEffect -> oldState
            is ProductDetailsEffect.ChangeQuantityEffect -> ProductDetailsViewState(
                isInitialLoading = oldState.isInitialLoading,
                initialLoadingError = oldState.initialLoadingError,
                product = oldState.product,
                quantity = oldState.quantity + effect.quantityChange,
                cartInformation = oldState.cartInformation
            )
        }

}