package com.example.onto.discount

import com.example.onto.data.remote.OntoApiService
import com.example.onto.utils.Result
import com.example.onto.vo.OntoOffer
import javax.inject.Inject

class DiscountRepository @Inject  constructor(private val apiService: OntoApiService) {
    suspend fun getStocks(): Result<List<OntoOffer>> {

        val response = apiService.getOffers()
        return if (response.isSuccessful && response.body() != null) {
            Result.Success(response.body()!!.data.offers)
        } else {
            Result.Error(Throwable(response.message()))
        }
    }
}
