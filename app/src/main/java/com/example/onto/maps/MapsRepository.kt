package com.example.onto.maps

import com.example.onto.data.remote.OntoApiService
import com.example.onto.vo.response.*
import javax.inject.Inject


class MapsRepository @Inject constructor(
    private val apiService: OntoApiService
) {
    suspend fun getShops(): OntoResponse<ShopsData> = apiService.getShops()
}