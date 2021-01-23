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
    abstract fun bindProductsUseCase(
        productsUseCaseImpl: ProductsUseCaseImpl
    ): ProductsUseCase

    @Binds
    abstract fun bindProfileUseCase(
        profileUseCaseImpl: ProfileUseCaseImpl
    ): ProfileUseCase

    @Binds
    abstract fun bindProductDetailsUseCase(
        productDetailsUseCaseImpl: ProductDetailsUseCaseImpl
    ): ProductDetailsUseCase

    @Binds
    abstract fun bindOnboardingUseCase(
        onboardingUseCaseImpl: OnboardingUseCaseImpl
    ): OnboardingUseCase

    @Binds
    abstract fun bindProfilePrefsUseCase(
        profilePrefsUseCaseImpl: ProfilePrefsUseCaseImpl
    ): ProfilePrefsUseCase

    @Binds
    abstract fun bindOrderPrefsUseCase(
        orderPrefsUseCaseImpl: OrderPrefsUseCaseImpl
    ): OrderPrefsUseCase

    @Binds
    abstract fun bindQuestionUseCase(
        questionUseCaseImpl: QuestionUseCaseImpl
    ): QuestionUseCase

    @Binds
    abstract fun bindCartUseCase(
        cartUseCaseImpl: CartUseCaseImpl
    ): CartUseCase

}