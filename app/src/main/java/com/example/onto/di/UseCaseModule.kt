package com.example.onto.di

import com.example.onto.products.ProductsRepository
import com.example.onto.products.ProductsUseCase
import com.example.onto.products.ProductsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(
    ApplicationComponent::class
)
object UseCaseModule {
    @Provides
    fun provideProductsUseCase(
        @RemoteProductsRepository remoteProductsRepository: ProductsRepository
    ): ProductsUseCase = ProductsUseCaseImpl(remoteProductsRepository)
}