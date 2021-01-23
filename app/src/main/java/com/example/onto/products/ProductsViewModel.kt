package com.example.onto.products

import androidx.hilt.lifecycle.ViewModelInject
import com.example.onto.base.BaseViewModel
import com.example.onto.data.usecase.CartUseCase
import com.example.onto.data.usecase.ProductsUseCase
import com.example.onto.navigation.Coordinator
import com.example.onto.utils.Result

class ProductsViewModel @ViewModelInject constructor(
    private val productsUseCase: ProductsUseCase,
    private val cartUseCase: CartUseCase,
    private val coordinator: Coordinator
) : BaseViewModel<ProductsViewState, ProductsEffect, ProductsIntent, ProductsAction>() {
    override fun initialState(): ProductsViewState = ProductsViewState.initialState

    override fun intentInterpreter(intent: ProductsIntent): ProductsAction =
        when (intent) {
            is ProductsIntent.InitialIntent -> ProductsAction.LoadProductsAction
            is ProductsIntent.ReloadIntent -> ProductsAction.LoadProductsAction
            is ProductsIntent.RefreshIntent -> ProductsAction.RefreshProductsAction
            is ProductsIntent.SwitchTagIntent -> ProductsAction.SwitchFilterTagAction(intent.tagId)
            is ProductsIntent.AddToCartIntent -> ProductsAction.AddToCartAction(intent.productId)
            ProductsIntent.OpenCartIntent -> ProductsAction.NavigateToCartAction
            is ProductsIntent.OpenProductDetailsIntent -> ProductsAction.NavigateToProductDetailsAction(
                intent.productId
            )
            ProductsIntent.UpdateCartIntent -> ProductsAction.UpdateCartAction
        }

    override suspend fun performAction(action: ProductsAction): ProductsEffect =
        when (action) {
            is ProductsAction.LoadProductsAction -> {
                addIntermediateEffect(ProductsEffect.InitialLoadingEffect)
                addIntermediateEffect(
                    ProductsEffect.CartInformationLoadedEffect(
                        when (val result = cartUseCase.getCartInformation()) {
                            is Result.Success -> result.data
                            is Result.Error -> null
                        }
                    )
                )
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
                cartUseCase.addCartItem(action.productId, 1)
                ProductsEffect.CartInformationLoadedEffect(
                    when (val result = cartUseCase.getCartInformation()) {
                        is Result.Success -> result.data
                        is Result.Error -> null
                    }
                )
            }
            ProductsAction.NavigateToCartAction -> {
                coordinator.navigateToCart()
                ProductsEffect.NoEffect
            }
            is ProductsAction.NavigateToProductDetailsAction -> {
                coordinator.navigateToProduct(action.productId)
                ProductsEffect.NoEffect
            }
            ProductsAction.UpdateCartAction -> ProductsEffect.CartInformationLoadedEffect(
                when (val result = cartUseCase.getCartInformation()) {
                    is Result.Success -> result.data
                    is Result.Error -> null
                }
            )
        }

    override fun stateReducer(
        oldState: ProductsViewState,
        effect: ProductsEffect
    ): ProductsViewState =
        when (effect) {
            is ProductsEffect.InitialLoadingEffect -> ProductsViewState.initialLoadingState
            is ProductsEffect.InitialLoadingErrorEffect -> ProductsViewState.initialErrorState(
                error = effect.throwable,
                cartInfo = oldState.cartInfo
            )
            is ProductsEffect.ProductsLoadedEffect -> ProductsViewState.productsLoadedState(
                products = effect.products,
                cartInfo = oldState.cartInfo
            )
            is ProductsEffect.RefreshLoadingEffect -> ProductsViewState.refreshLoadingState(
                products = oldState.products,
                cartInfo = oldState.cartInfo
            )
            is ProductsEffect.RefreshLoadingErrorEffect -> ProductsViewState.refreshLoadingErrorState(
                products = oldState.products,
                cartInfo = oldState.cartInfo,
                error = effect.throwable
            )
            is ProductsEffect.CartInformationLoadedEffect -> ProductsViewState.cartInfoLoaded(
                isInitialLoading = oldState.isInitialLoading,
                initialError = oldState.initialError,
                products = oldState.products,
                cartInfo = effect.cartInformation,
                isRefreshLoading = oldState.isRefreshLoading,
                refreshError = oldState.refreshError
            )
            ProductsEffect.NoEffect -> oldState
        }

}
