package com.example.onto.maps

import com.example.onto.ExampleEffect
import com.example.onto.base.MviEffect
import com.example.onto.vo.OntoShop

sealed class MapsEffect : MviEffect {

    object PermissionsCheckEffect : MapsEffect()

    object InitialLoadingEffect : MapsEffect()

    data class InitialLoadingErrorEffect(
        val throwable: Throwable
    ) : MapsEffect()

    data class ShopsLoadedEffect(
        val shops: List<OntoShop>
    ) : MapsEffect()
}