package com.example.onto.material

import androidx.hilt.lifecycle.ViewModelInject
import com.example.onto.base.BaseViewModel
import com.example.onto.materials.MaterialsRepository
import com.example.onto.utils.Result

class MaterialDetailsViewModel @ViewModelInject constructor(
    private val materialsRepository: MaterialsRepository
) : BaseViewModel<MaterialDetailsViewState, MaterialDetailsEffect, MaterialDetailsIntent, MaterialDetailsAction>() {
    override fun initialState(): MaterialDetailsViewState = MaterialDetailsViewState()

    override fun intentInterpreter(intent: MaterialDetailsIntent): MaterialDetailsAction =
        when (intent) {
            is MaterialDetailsIntent.InitialIntent -> MaterialDetailsAction.LoadProductDetailsAction(
                intent.articleId
            )
            is MaterialDetailsIntent.ReloadIntent -> MaterialDetailsAction.LoadProductDetailsAction(
                intent.articleId
            )
        }

    override suspend fun performAction(action: MaterialDetailsAction): MaterialDetailsEffect =
        when (action) {
            is MaterialDetailsAction.LoadProductDetailsAction -> {
                addIntermediateEffect(MaterialDetailsEffect.InitialLoadingEffect)
                when (val result = materialsRepository.getMaterials()) {
                    is Result.Success -> MaterialDetailsEffect.MaterialDetailsLoadedEffect(result.data[0])
                    is Result.Error -> MaterialDetailsEffect.InitialLoadingErrorEffect(result.throwable)
                }
            }
        }

    override fun stateReducer(
        oldState: MaterialDetailsViewState,
        effect: MaterialDetailsEffect
    ): MaterialDetailsViewState =
        when (effect) {
            MaterialDetailsEffect.InitialLoadingEffect -> MaterialDetailsViewState(
                isInitialLoading = true,
                initialLoadingError = null,
                article = null
            )
            is MaterialDetailsEffect.InitialLoadingErrorEffect -> MaterialDetailsViewState(
                isInitialLoading = false,
                initialLoadingError = effect.throwable,
                article = null
            )
            is MaterialDetailsEffect.MaterialDetailsLoadedEffect -> MaterialDetailsViewState(
                isInitialLoading = false,
                initialLoadingError = null,
                article = effect.material
            )
        }

}