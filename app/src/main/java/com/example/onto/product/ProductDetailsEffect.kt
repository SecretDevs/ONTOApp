package com.example.onto.product

import com.example.onto.base.MviEffect
import com.example.onto.vo.OntoProduct
import com.example.onto.vo.OntoUser

sealed class ProductDetailsEffect : MviEffect {

    object InitialLoadingEffect : ProductDetailsEffect()

    data class InitialLoadingErrorEffect(
        val throwable: Throwable
    ) : ProductDetailsEffect()

    data class ProductDetailsLoadedEffect(
        val product: OntoProduct
    ) : ProductDetailsEffect()

}