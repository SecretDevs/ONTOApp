package com.example.onto.materials

import com.example.onto.base.MviEffect
import com.example.onto.vo.inapp.CartInformation
import com.example.onto.vo.remote.OntoArticle

sealed class MaterialEffect : MviEffect {
    object InitialLoadingEffect : MaterialEffect()

    data class InitialLoadingErrorEffect(
        val throwable: Throwable
    ) : MaterialEffect()

    data class MaterialLoadedEffect(
        val products: List<OntoArticle>
    ) : MaterialEffect()

    object RefreshLoadingEffect : MaterialEffect()

    data class RefreshLoadingErrorEffect(
        val throwable: Throwable
    ) : MaterialEffect()

    data class CartInformationLoadedEffect(
        val cartInformation: CartInformation?
    ) : MaterialEffect()

    object NoEffect : MaterialEffect()

}