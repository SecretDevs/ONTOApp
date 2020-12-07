package com.example.onto.di

import com.example.onto.data.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun provideProductsUseCase(
        productsUseCaseImpl: ProductsUseCaseImpl
    ): ProductsUseCase

    @Binds
    abstract fun provideProfileUseCase(
        profileUseCaseImpl: ProfileUseCaseImpl
    ): ProfileUseCase

    @Binds
    abstract fun provideProductDetailsUseCase(
        productDetailsUseCaseImpl: ProductDetailsUseCaseImpl
    ): ProductDetailsUseCase

    @Binds
    abstract fun provideSharedPreferenceUseCase(
        sharedPreferenceUseCaseImpl: SharedPreferenceUseCaseImpl
    ): SharedPreferenceUseCase

    @Binds
    abstract fun provideCartUseCase(
        cartUseCaseImpl: CartUseCaseImpl
    ): CartUseCase

}