package com.example.onto.maps

import com.example.onto.ExampleEffect
import com.example.onto.base.MviEffect
import com.example.onto.vo.OntoShop

sealed class MapsEffect : MviEffect {
    class RetrieveShops(
        val ontoShops: List<OntoShop>
    ) : MapsEffect()

    class ChooseShop(
        val nextShop: OntoShop
    ) : MapsEffect()

    class FindUserLocation() : MapsEffect()
}