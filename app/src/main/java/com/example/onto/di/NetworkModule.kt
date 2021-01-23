package com.example.onto.di

import com.example.onto.data.remote.OntoApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    fun provideOntoApiService(moshi: Moshi): OntoApiService =
        Retrofit.Builder()
            .baseUrl("https://onto-ipc.herokuapp.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(OntoApiService::class.java)

}