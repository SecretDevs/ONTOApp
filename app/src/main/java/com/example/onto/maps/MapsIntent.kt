package com.example.onto.maps

import com.example.onto.base.MviIntent

sealed class MapsIntent : MviIntent {
    object InitialIntent : MapsIntent()
    object ReloadIntent : MapsIntent()
    object MapLoadedIntent : MapsIntent()
    data class ShowShopInfoIntent(
        val shop: Pair<Double, Double>
    ) : MapsIntent()
}