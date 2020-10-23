package com.example.onto.data.remote

import com.example.onto.vo.OntoResponse
import com.example.onto.vo.OntoShop
import com.example.onto.vo.ShopsData
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface OntoApiService {
    @GET("shops")
    suspend fun getShops() : OntoResponse
}