package com.example.onto.maps

import com.example.onto.base.MviAction

sealed class MapsAction : MviAction {
    object LoadShopsAction : MapsAction()
    object LoadedMapAction : MapsAction()
    data class SelectShopAction(
        val shop: Pair<Double, Double>
    ) : MapsAction()
}