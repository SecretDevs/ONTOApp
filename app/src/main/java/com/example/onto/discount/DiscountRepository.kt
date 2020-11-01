package com.example.onto.discount

import com.example.onto.data.remote.OntoApiService
import com.example.onto.utils.Result
import com.example.onto.vo.OntoArticle
import javax.inject.Inject

class DiscountRepository @Inject  constructor(private val apiService: OntoApiService) {
    suspend fun getStocks(): Result<List<OntoArticle>> {

        val response = apiService.getArticles()
        return if (response.isSuccessful && response.body() != null) {
            Result.Success(response.body()!!.data.articles)
        } else {
            Result.Error(Throwable(response.message()))
        }
    }
}
