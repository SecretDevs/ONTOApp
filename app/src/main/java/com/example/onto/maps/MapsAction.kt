package com.example.onto.maps

import com.example.onto.base.MviAction

sealed class MapsAction : MviAction {

    object RetrieveShops : MapsAction()

    object ChooseNextShop : MapsAction()

    object FindUserLocation : MapsAction()
}