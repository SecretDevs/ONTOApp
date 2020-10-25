package com.example.onto.materials

import com.example.onto.base.MviEffect
import com.example.onto.products.ProductsEffect
import com.example.onto.vo.OntoArticle
import com.example.onto.vo.OntoProduct

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
}