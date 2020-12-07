package com.example.onto.maps

import com.example.onto.base.MviAction

sealed class MapsAction : MviAction {

    object LoadShopsAction : MapsAction()
    object CheckPermissionsAction : MapsAction()
    object LoadedMapAction : MapsAction()

}