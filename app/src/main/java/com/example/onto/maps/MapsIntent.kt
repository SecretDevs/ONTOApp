package com.example.onto.maps

import com.example.onto.base.MviIntent
import com.example.onto.base.NothingIntent

sealed class MapsIntent : MviIntent {
    object PermissionsCheckIntent : MapsIntent()
    object InitialIntent : MapsIntent()
    object ReloadIntent : MapsIntent()
    object MapLoadedIntent : MapsIntent()
    object OpenCartIntent : MapsIntent()
    object MapsUpdateCart : MapsIntent()
}