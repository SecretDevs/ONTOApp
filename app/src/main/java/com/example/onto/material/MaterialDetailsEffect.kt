package com.example.onto.material

import com.example.onto.base.MviEffect
import com.example.onto.vo.OntoArticle

sealed class MaterialDetailsEffect : MviEffect {

    object InitialLoadingEffect : MaterialDetailsEffect()

    data class InitialLoadingErrorEffect(
        val throwable: Throwable
    ) : MaterialDetailsEffect()

    data class MaterialDetailsLoadedEffect(
        val material: OntoArticle
    ) : MaterialDetailsEffect()

}