package com.example.onto.discount

import com.example.onto.base.MviEffect
import com.example.onto.vo.inapp.CartInformation
import com.example.onto.vo.remote.OntoOffer

sealed class DiscountEffect : MviEffect {
    object InitialLoadingEffect : DiscountEffect()

    data class InitialLoadingErrorEffect(
        val throwable: Throwable
    ) : DiscountEffect()

    data class DiscountLoadedEffect(
        val offers: List<OntoOffer>
    ) : DiscountEffect()

    object RefreshLoadingEffect : DiscountEffect()

    data class RefreshLoadingErrorEffect(
        val throwable: Throwable
    ) : DiscountEffect()

    data class CartInformationLoadedEffect(
        val cartInformation: CartInformation?
    ) : DiscountEffect()

    object NoEffect : DiscountEffect()

}