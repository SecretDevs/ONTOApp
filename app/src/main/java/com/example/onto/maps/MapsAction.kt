package com.example.onto.maps

import com.example.onto.ExampleAction
import com.example.onto.base.MviAction
import com.example.onto.vo.OntoShop

sealed class MapsAction : MviAction {

    object RetrieveShops : MapsAction()

    object ChooseNextShop : MapsAction()

    object FindUserLocation : MapsAction()
}