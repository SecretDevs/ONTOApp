package com.example.onto.di

import com.example.onto.data.remote.OntoApiService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(
    ApplicationComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOntoApiService(): OntoApiService {
        return Retrofit.Builder()
            .baseUrl("https://onto-ipc.herokuapp.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(OntoApiService::class.java)
    }
}