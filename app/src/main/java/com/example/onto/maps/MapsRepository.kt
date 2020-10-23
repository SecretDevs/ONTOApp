package com.example.onto.maps

import com.example.onto.data.remote.OntoApiService
import com.example.onto.vo.OntoResponse
import com.example.onto.vo.OntoShop
import com.example.onto.vo.ShopsData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class MapsRepository @Inject constructor(
    private val apiService: OntoApiService
) {
    suspend fun getShops(): OntoResponse = apiService.getShops()
}