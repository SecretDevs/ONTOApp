package com.example.onto.materials

import androidx.hilt.lifecycle.ViewModelInject
import com.example.onto.base.BaseViewModel
import com.example.onto.data.usecase.CartUseCase
import com.example.onto.navigation.Coordinator
import com.example.onto.utils.Result

class MaterialViewModel @ViewModelInject constructor(
    private val repository: MaterialsRepository,
    private val cartUseCase: CartUseCase,
    private val coordinator: Coordinator
) : BaseViewModel<MaterialViewState, MaterialEffect, MaterialIntent, MaterialAction>() {

    override fun initialState(): MaterialViewState = MaterialViewState.initialState

    override fun intentInterpreter(intent: MaterialIntent): MaterialAction =
        when (intent) {
            is MaterialIntent.InitialIntent -> MaterialAction.LoadProductsAction
            is MaterialIntent.ReloadIntent -> MaterialAction.LoadProductsAction
            is MaterialIntent.RefreshIntent -> MaterialAction.RefreshProductsAction
            is MaterialIntent.NavigateToMaterialDetailsIntent -> MaterialAction.NavigateToProductAction(
                intent.materialId
            )
            MaterialIntent.NavigateToCartIntent -> MaterialAction.NavigateToCartAction
            MaterialIntent.MaterialsNothingIntent -> throw IllegalArgumentException("Nothing intent interpreting")
        }

    override suspend fun performAction(action: MaterialAction): MaterialEffect =
        when (action) {
            is MaterialAction.LoadProductsAction -> {
                addIntermediateEffect(MaterialEffect.InitialLoadingEffect)
                addIntermediateEffect(
                    MaterialEffect.CartInformationLoadedEffect(
                        when (val result = cartUseCase.getCartInformation()) {
                            is Result.Success -> result.data
                            is Result.Error -> null
                        }
                    )
                )
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
            MaterialAction.NavigateToCartAction -> {
                coordinator.navigateToCart()
                MaterialEffect.NoEffect
            }
            is MaterialAction.NavigateToProductAction -> {
                coordinator.navigateToArticle(action.productId)
                MaterialEffect.NoEffect
            }
        }

    override fun stateReducer(
        oldState: MaterialViewState,
        effect: MaterialEffect
    ): MaterialViewState =
        when (effect) {
            is MaterialEffect.InitialLoadingEffect -> MaterialViewState.initialLoadingState
            is MaterialEffect.InitialLoadingErrorEffect -> MaterialViewState.initialErrorState(
                error = effect.throwable,
                cartInformation = oldState.cartInformation
            )
            is MaterialEffect.MaterialLoadedEffect -> MaterialViewState.productsLoadedState(
                products = effect.products,
                cartInformation = oldState.cartInformation
            )
            is MaterialEffect.RefreshLoadingEffect -> MaterialViewState.refreshLoadingState(
                products = oldState.products,
                cartInformation = oldState.cartInformation
            )
            is MaterialEffect.RefreshLoadingErrorEffect -> MaterialViewState.refreshLoadingErrorState(
                products = oldState.products,
                cartInformation = oldState.cartInformation,
                error = effect.throwable
            )
            is MaterialEffect.CartInformationLoadedEffect -> MaterialViewState.cartInformationLoaded(
                products = oldState.products,
                cartInformation = effect.cartInformation,
                isInitialLoading = oldState.isInitialLoading,
                initialError = oldState.initialError,
                refreshError = oldState.refreshError,
                isRefreshLoading = oldState.isRefreshLoading
            )
            MaterialEffect.NoEffect -> oldState
        }

}