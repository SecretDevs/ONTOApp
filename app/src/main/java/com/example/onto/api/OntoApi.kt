package com.example.onto.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class OntoApi {
    companion object{
        val instance = Retrofit.Builder()
            .baseUrl("https://onto-ipc.herokuapp.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}