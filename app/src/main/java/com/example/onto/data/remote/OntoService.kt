package com.example.onto.data.remote

import com.example.onto.vo.OntoResponse
import com.example.onto.vo.OntoShop
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface OntoService {
    @GET("shops")
    suspend fun getShops() : Response<OntoResponse<MutableList<OntoShop>>>
}