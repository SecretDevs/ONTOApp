package com.example.onto.maps

import com.example.onto.base.MviIntent

sealed class MapsIntent : MviIntent {
    object PermissionsCheckIntent : MapsIntent()
    object InitialIntent : MapsIntent()
    object ReloadIntent : MapsIntent()
}