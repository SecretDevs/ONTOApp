package com.example.onto.materials

import com.example.onto.data.remote.OntoApiService
import com.example.onto.utils.Result
import com.example.onto.vo.OntoArticle
import com.example.onto.vo.response.ArticlesData
import com.example.onto.vo.response.OntoResponse
import com.example.onto.vo.response.ShopsData
import javax.inject.Inject

class MaterialsRepository @Inject  constructor(private val apiService: OntoApiService) {
    suspend fun getMaterials(): Result<List<OntoArticle>> {

        val response = apiService.getArticles()
        return if (response.isSuccessful && response.body() != null) {
            Result.Success(response.body()!!.data.articles)
        } else {
            Result.Error(Throwable(response.message()))
        }
    }
}
