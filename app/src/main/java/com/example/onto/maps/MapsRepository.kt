package com.example.onto.maps

import com.example.onto.api.OntoApi
import com.example.onto.api.OntoApiService
import com.example.onto.vo.OntoShop

class MapsRepository {
    fun getOntoShops(): MutableList<OntoShop>{
        OntoApi.instance.create(OntoApiService::class.java)
        //TODO
        return ArrayList()
    }
}