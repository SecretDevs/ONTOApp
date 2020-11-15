package com.example.onto.maps

import com.example.onto.base.MviEffect
import com.example.onto.vo.OntoShop

sealed class MapsEffect : MviEffect {

    object MapLoadedEffect : MapsEffect()

    object InitialLoadingEffect : MapsEffect()

    data class InitialLoadingErrorEffect(
        val throwable: Throwable
    ) : MapsEffect()

    data class ShopsLoadedEffect(
        val shops: List<OntoShop>
    ) : MapsEffect()

    data class ShowShopInfoEffect(
        val shop: Pair<Double, Double>
    ) : MapsEffect()

}