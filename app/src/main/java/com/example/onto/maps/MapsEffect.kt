package com.example.onto.maps

import com.example.onto.base.MviEffect
import com.example.onto.vo.remote.OntoShop

sealed class MapsEffect : MviEffect {

    object PermissionsCheckEffect : MapsEffect()

    object InitialLoadingEffect : MapsEffect()

    object MapLoadedEffect : MapsEffect()

    data class InitialLoadingErrorEffect(
        val throwable: Throwable
    ) : MapsEffect()

    data class ShopsLoadedEffect(
        val shops: List<OntoShop>
    ) : MapsEffect()
}