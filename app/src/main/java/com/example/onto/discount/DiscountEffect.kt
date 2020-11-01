package com.example.onto.discount

import com.example.onto.base.MviEffect
import com.example.onto.vo.OntoArticle

sealed class DiscountEffect : MviEffect {
    object InitialLoadingEffect : DiscountEffect()

    data class InitialLoadingErrorEffect(
        val throwable: Throwable
    ) : DiscountEffect()

    data class DiscountLoadedEffect(
        val products: List<OntoArticle>
    ) : DiscountEffect()

    object RefreshLoadingEffect : DiscountEffect()

    data class RefreshLoadingErrorEffect(
        val throwable: Throwable
    ) : DiscountEffect()
}