package com.example.onto.products

import com.example.onto.base.MviEffect
import com.example.onto.vo.inapp.CartInformation
import com.example.onto.vo.inapp.OntoProduct

sealed class ProductsEffect : MviEffect {

    object InitialLoadingEffect : ProductsEffect()

    data class InitialLoadingErrorEffect(
        val throwable: Throwable
    ) : ProductsEffect()

    data class ProductsLoadedEffect(
        val products: List<OntoProduct>
    ) : ProductsEffect()

    object RefreshLoadingEffect : ProductsEffect()

    data class RefreshLoadingErrorEffect(
        val throwable: Throwable
    ) : ProductsEffect()

    data class CartInformationLoadedEffect(
        val cartInformation: CartInformation?
    ) : ProductsEffect()

    object NoEffect : ProductsEffect()

}