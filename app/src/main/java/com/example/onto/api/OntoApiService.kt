package com.example.onto.api

import com.example.onto.vo.OntoShop
import retrofit2.Call
import retrofit2.http.GET

interface OntoApiService {
    @GET("shops")
    fun getShops() : Call<MutableList<OntoShop>>
}