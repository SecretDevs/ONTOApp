package com.example.onto.maps

import com.example.onto.base.MviIntent

sealed class MapsIntent : MviIntent {
    object InitialIntent : MapsIntent()
    object ChooseShopIntent : MapsIntent()
    object UserLocationIntent : MapsIntent()
}