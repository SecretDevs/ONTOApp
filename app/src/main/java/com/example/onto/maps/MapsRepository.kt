package com.example.onto.maps

import com.example.onto.data.remote.OntoApiService
import com.example.onto.utils.Result
import com.example.onto.vo.OntoShop
import timber.log.Timber
import javax.inject.Inject


class MapsRepository @Inject constructor(
    private val apiService: OntoApiService
) {
    suspend fun getShops(): Result<List<OntoShop>> {
        return try {
            val response = apiService.getShops()
            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!.data.shops)
            } else {
                Result.Error(Throwable(response.message()))
            }
        } catch (e: Exception) {
            Timber.e(e)
            Result.Error(e)
        }
    }
}