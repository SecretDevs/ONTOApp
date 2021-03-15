package com.example.onto.maps

import com.example.onto.base.MviEffect
import com.example.onto.vo.inapp.CartInformation
import com.example.onto.vo.remote.OntoShop

sealed class MapsEffect : MviEffect {

    object PermissionsCheckEffect : MapsEffect()

    object InitialLoadingEffect : MapsEffect()

    data class MapLoadedEffect(
        val cartInformation: CartInformation?
    ) : MapsEffect()

    object NoEffect : MapsEffect()

    data class InitialLoadingErrorEffect(
        val throwable: Throwable
    ) : MapsEffect()

    data class ShopsLoadedEffect(
        val shops: List<OntoShop>
    ) : MapsEffect()

    data class CartInformationLoadedEffect(
        val cartInformation: CartInformation?
    ) : MapsEffect()

    data class BackstackCartInformationLoadedEffect(
        val cartInformation: CartInformation?
    ) : MapsEffect()

}