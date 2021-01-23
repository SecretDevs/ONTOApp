package com.example.onto.di

import com.example.onto.navigation.Coordinator
import com.example.onto.navigation.CoordinatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class MainActivityModule {
    @Binds
    @ActivityScoped
    abstract fun bindCoordinator(
        coordinatorImpl: CoordinatorImpl
    ): Coordinator
}