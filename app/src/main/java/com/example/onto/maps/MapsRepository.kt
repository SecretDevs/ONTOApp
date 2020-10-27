package com.example.onto.maps

import com.example.onto.data.remote.OntoApiService
import com.example.onto.vo.OntoShop
import javax.inject.Inject
import com.example.onto.utils.Result


class MapsRepository @Inject constructor(
    private val apiService: OntoApiService
) {
    suspend fun getShops(): Result<List<OntoShop>> {
        val response = apiService.getShops()
        return if (response.isSuccessful && response.body() != null) {
            Result.Success(response.body()!!.data.shops)
        } else {
            Result.Error(Throwable(response.message()))
        }
    }
}