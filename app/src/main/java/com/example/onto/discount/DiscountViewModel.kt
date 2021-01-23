package com.example.onto.discount

import androidx.hilt.lifecycle.ViewModelInject
import com.example.onto.base.BaseViewModel
import com.example.onto.data.usecase.CartUseCase
import com.example.onto.navigation.Coordinator
import com.example.onto.utils.Result

class DiscountViewModel @ViewModelInject constructor(
    private val repository: DiscountRepository,
    private val cartUseCase: CartUseCase,
    private val coordinator: Coordinator
) : BaseViewModel<DiscountViewState, DiscountEffect, DiscountIntent, DiscountAction>() {

    override fun initialState(): DiscountViewState = DiscountViewState.initialState

    override fun intentInterpreter(intent: DiscountIntent): DiscountAction =
        when (intent) {
            is DiscountIntent.InitialIntent -> DiscountAction.LoadProductsAction
            is DiscountIntent.ReloadIntent -> DiscountAction.LoadProductsAction
            is DiscountIntent.RefreshIntent -> DiscountAction.RefreshProductsAction
            is DiscountIntent.AddProductToCartIntent -> DiscountAction.AddProductToCart(intent.productId)
            is DiscountIntent.NavigateToDiscountDetailsIntent -> DiscountAction.NavigateToProductAction(
                intent.discountId
            )
            DiscountIntent.NavigateToCartIntent -> DiscountAction.NavigateToCartAction
            DiscountIntent.UpdateCartIntent -> DiscountAction.UpdateCartAction
        }

    override suspend fun performAction(action: DiscountAction): DiscountEffect =
        when (action) {
            is DiscountAction.LoadProductsAction -> {
                addIntermediateEffect(DiscountEffect.InitialLoadingEffect)
                addIntermediateEffect(
                    DiscountEffect.CartInformationLoadedEffect(
                        when (val result = cartUseCase.getCartInformation()) {
                            is Result.Success -> result.data
                            is Result.Error -> null
                        }
                    )
                )
                when (val result = repository.getStocks()) {
                    is Result.Success -> DiscountEffect.DiscountLoadedEffect(result.data)
                    is Result.Error -> DiscountEffect.InitialLoadingErrorEffect(result.throwable)
                }
            }
            is DiscountAction.RefreshProductsAction -> {
                addIntermediateEffect(DiscountEffect.RefreshLoadingEffect)
                addIntermediateEffect(
                    DiscountEffect.CartInformationLoadedEffect(
                        when (val result = cartUseCase.getCartInformation()) {
                            is Result.Success -> result.data
                            is Result.Error -> null
                        }
                    )
                )
                when (val result = repository.getStocks()) {
                    is Result.Success -> DiscountEffect.DiscountLoadedEffect(result.data)
                    is Result.Error -> DiscountEffect.RefreshLoadingErrorEffect(result.throwable)
                }
            }
            is DiscountAction.AddProductToCart -> {
                cartUseCase.addCartItem(action.productId, 1)
                DiscountEffect.NoEffect
            }
            DiscountAction.NavigateToCartAction -> {
                coordinator.navigateToCart()
                DiscountEffect.NoEffect
            }
            is DiscountAction.NavigateToProductAction -> {
                coordinator.navigateToProduct(action.productId)
                DiscountEffect.NoEffect
            }
            DiscountAction.UpdateCartAction -> DiscountEffect.CartInformationLoadedEffect(
                when (val result = cartUseCase.getCartInformation()) {
                    is Result.Success -> result.data
                    is Result.Error -> null
                }
            )
        }

    override fun stateReducer(
        oldState: DiscountViewState,
        effect: DiscountEffect
    ): DiscountViewState =
        when (effect) {
            is DiscountEffect.InitialLoadingEffect -> DiscountViewState.initialLoadingState
            is DiscountEffect.InitialLoadingErrorEffect -> DiscountViewState.initialErrorState(
                error = effect.throwable,
                cartInformation = oldState.cartInformation
            )
            is DiscountEffect.DiscountLoadedEffect -> DiscountViewState.productsLoadedState(
                offers = effect.offers,
                cartInformation = oldState.cartInformation
            )
            is DiscountEffect.RefreshLoadingEffect -> DiscountViewState.refreshLoadingState(
                offers = oldState.offers,
                cartInformation = oldState.cartInformation
            )
            is DiscountEffect.RefreshLoadingErrorEffect -> DiscountViewState.refreshLoadingErrorState(
                offers = oldState.offers,
                error = effect.throwable,
                cartInformation = oldState.cartInformation
            )
            is DiscountEffect.CartInformationLoadedEffect -> DiscountViewState.cartInformationLoaded(
                isInitialLoading = oldState.isInitialLoading,
                initialError = oldState.initialError,
                offers = oldState.offers,
                cartInformation = effect.cartInformation,
                isRefreshLoading = oldState.isRefreshLoading,
                refreshError = oldState.refreshError
            )
            DiscountEffect.NoEffect -> oldState
        }

}