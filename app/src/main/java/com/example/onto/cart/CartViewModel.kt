package com.example.onto.cart

import androidx.hilt.lifecycle.ViewModelInject
import com.example.onto.base.BaseViewModel
import com.example.onto.data.usecase.CartUseCase
import com.example.onto.navigation.Coordinator
import com.example.onto.utils.Result

class CartViewModel @ViewModelInject constructor(
    private val cartUseCase: CartUseCase,
    private val coordinator: Coordinator
) : BaseViewModel<CartViewState, CartEffect, CartIntent, CartAction>() {
    override fun initialState(): CartViewState = CartViewState.initialState

    override fun intentInterpreter(intent: CartIntent): CartAction =
        when (intent) {
            CartIntent.InitialIntent -> CartAction.LoadCartAction
            CartIntent.ReloadIntent -> CartAction.LoadCartAction
            CartIntent.OpenShopIntent -> CartAction.OpenShopAction
            CartIntent.OpenOrderHistoryIntent -> CartAction.OpenOrderHistoryAction
            CartIntent.GoBackIntent -> CartAction.GoBackAction
            is CartIntent.RemoveProductIntent -> CartAction.ChangeItemCountAction(
                intent.productId,
                0
            )
            is CartIntent.AddOneItemForProductIntent -> CartAction.ChangeItemCountAction(
                intent.productId,
                1
            )
            is CartIntent.RemoveOneItemForProductIntent -> CartAction.ChangeItemCountAction(
                intent.productId,
                -1
            )
            CartIntent.CartNothingIntent -> throw IllegalArgumentException("Nothing intent interpreting")
            CartIntent.CheckoutIntent -> CartAction.CheckoutAction
            is CartIntent.OpenProductDetailsIntent -> CartAction.OpenProductDetailsAction(intent.productId)
        }

    override suspend fun performAction(action: CartAction): CartEffect =
        when (action) {
            CartAction.LoadCartAction -> {
                addIntermediateEffect(CartEffect.InitialLoadingEffect)
                when (val result = cartUseCase.getCartProducts()) {
                    is Result.Success -> CartEffect.CartLoadedEffect(result.data)
                    is Result.Error -> CartEffect.InitialLoadingErrorEffect(result.throwable)
                }
            }
            CartAction.OpenShopAction -> {
                coordinator.navigateToShop()
                CartEffect.NoEffect
            }
            CartAction.OpenOrderHistoryAction -> {
                coordinator.navigateToOrderHistory()
                CartEffect.NoEffect
            }
            CartAction.GoBackAction -> {
                coordinator.pop()
                CartEffect.NoEffect
            }
            is CartAction.ChangeItemCountAction -> {
                when (val result = cartUseCase.updateCartItem(action.productId, action.change)) {
                    is Result.Success -> CartEffect.CartLoadedEffect(result.data)
                    is Result.Error -> CartEffect.QuantityErrorEffect(result.throwable)
                }
            }
            CartAction.CheckoutAction -> {
                coordinator.navigateToOrder()
                CartEffect.NoEffect
            }
            is CartAction.OpenProductDetailsAction -> {
                coordinator.navigateToProduct(action.productId)
                CartEffect.NoEffect
            }
        }

    override fun stateReducer(oldState: CartViewState, effect: CartEffect): CartViewState =
        when (effect) {
            CartEffect.InitialLoadingEffect -> CartViewState.loadingState
            is CartEffect.InitialLoadingErrorEffect -> CartViewState.loadingErrorState(effect.throwable)
            is CartEffect.CartLoadedEffect -> CartViewState.cartLoadedState(effect.data)
            CartEffect.NoEffect -> oldState
            is CartEffect.QuantityErrorEffect -> CartViewState.quantityChangeError(
                oldState.data,
                effect.throwable
            )
        }


}