package com.example.onto.cart

import com.example.onto.base.MviEffect
import com.example.onto.vo.inapp.CartItem

sealed class CartEffect : MviEffect {

    object InitialLoadingEffect : CartEffect()

    data class InitialLoadingErrorEffect(
        val throwable: Throwable
    ) : CartEffect()

    data class CartLoadedEffect(
        val data: List<CartItem>
    ) : CartEffect()

    object NoEffect : CartEffect()

    data class QuantityErrorEffect(
        val throwable: Throwable
    ) : CartEffect()

}