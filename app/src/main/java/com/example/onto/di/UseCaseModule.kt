package com.example.onto.di

import com.example.onto.product.ProductDetailsUseCase
import com.example.onto.product.ProductDetailsUseCaseImpl
import com.example.onto.products.ProductsDataSource
import com.example.onto.products.ProductsUseCase
import com.example.onto.products.ProductsUseCaseImpl
import com.example.onto.profile.ProfileDataSource
import com.example.onto.profile.ProfileUseCase
import com.example.onto.profile.ProfileUseCaseImpl
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
        @RemoteDataSource remoteProductsDataSource: ProductsDataSource
    ): ProductsUseCase = ProductsUseCaseImpl(remoteProductsDataSource)

    @Provides
    fun provideProfileUseCase(
        @RemoteDataSource remoteProfileDataSource: ProfileDataSource
    ): ProfileUseCase = ProfileUseCaseImpl(remoteProfileDataSource)

    @Provides
    fun provideProductDetailsUseCase(
        @RemoteDataSource remoteProductsDataSource: ProductsDataSource
    ): ProductDetailsUseCase = ProductDetailsUseCaseImpl(remoteProductsDataSource)

}