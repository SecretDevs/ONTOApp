package com.example.onto.di

import com.example.onto.data.remote.OntoApiService
import com.example.onto.products.ProductsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Qualifier

@Module
@InstallIn(
    ApplicationComponent::class
)
object RepositoryModule {
    @RemoteProductsRepository
    @Provides
    fun provideRemoteProductsRepository(
        service: OntoApiService
    ) : ProductsRepository = com.example.onto.products.RemoteProductsRepository(service)
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteProductsRepository

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalProductsRepository