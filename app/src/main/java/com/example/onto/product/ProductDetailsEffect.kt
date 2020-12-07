package com.example.onto.product

import com.example.onto.base.MviEffect
import com.example.onto.discount.DiscountEffect
import com.example.onto.vo.inapp.CartInformation
import com.example.onto.vo.inapp.OntoProduct

sealed class ProductDetailsEffect : MviEffect {

    object InitialLoadingEffect : ProductDetailsEffect()

    data class InitialLoadingErrorEffect(
        val throwable: Throwable
    ) : ProductDetailsEffect()

    data class ProductDetailsLoadedEffect(
        val product: OntoProduct
    ) : ProductDetailsEffect()

    data class CartInformationLoadedEffect(
        val cartInformation: CartInformation?
    ) : ProductDetailsEffect()

    data class ChangeQuantityEffect(
        val quantityChange: Int
    ) : ProductDetailsEffect()

    object NoEffect : ProductDetailsEffect()

}