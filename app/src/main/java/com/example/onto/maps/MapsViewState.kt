package com.example.onto.maps

import com.example.onto.base.MviViewState
import com.example.onto.vo.OntoShop

class MapsViewState(
    val ontoShopsList: List<OntoShop>? = ArrayList<OntoShop>(),
    val currentShop: OntoShop? = null
) : MviViewState