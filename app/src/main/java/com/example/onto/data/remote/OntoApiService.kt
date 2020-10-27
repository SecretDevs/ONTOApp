package com.example.onto.data.remote

import com.example.onto.vo.OntoProduct
import com.example.onto.vo.OntoUser
import com.example.onto.vo.response.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface OntoApiService {
    @GET("shops")
    suspend fun getShops() : Response<OntoResponse<ShopsData>>

    @GET("products")
    suspend fun getProducts() : OntoResponse<ProductsData>

    @GET("product_info/{product_id}")
    suspend fun getProductById(@Path("product_id") productId : String) : OntoResponse<OntoProduct>

    @GET("offers")
    suspend fun getOffers() : OntoResponse<OffersData>

    @GET("articles")
    suspend fun getArticles() : OntoResponse<ArticlesData>

    @GET("points")
    suspend fun getPoints() : OntoResponse<PointsData>

    @GET("user/{user_id}")
    suspend fun getUserById(@Path("user_id") userId : String) : OntoResponse<OntoUser>
}