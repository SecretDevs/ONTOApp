package com.example.onto.data.remote

import com.example.onto.vo.OntoProduct
import com.example.onto.vo.OntoUser
import com.example.onto.vo.response.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface OntoApiService {
    @GET("shops")
    suspend fun getShops():  Response<OntoResponse<ShopsData>>

    @GET("products")
    suspend fun getProducts(): Response<OntoResponse<ProductsData>>

    @GET("product_info")
    suspend fun getProductById(): Response<OntoResponse<OntoProduct>>

    @GET("offers")
    suspend fun getOffers(): Response<OntoResponse<OffersData>>

    @GET("articles")
    suspend fun getArticles(): Response<OntoResponse<ArticlesData>>

    @GET("points")
    suspend fun getPoints(): OntoResponse<PointsData>

    @GET("user")
    suspend fun getUserById(): Response<OntoResponse<OntoUser>>
}