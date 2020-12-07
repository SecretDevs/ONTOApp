package com.example.onto.di

import com.example.onto.data.datasource.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Qualifier

@Module
@InstallIn(ApplicationComponent::class)
abstract class DataSourceModule {
    @RemoteDataSource
    @Binds
    abstract fun provideRemoteProductsDataSource(
        remoteProductsDataSource: RemoteProductsDataSource
    ): ProductsDataSource

    @LocalDataSource
    @Binds
    abstract fun provideLocalProductsDataSource(
        localProductsDataSource: LocalProductsDataSource
    ): ProductsDataSource

    @RemoteDataSource
    @Binds
    abstract fun provideRemoteProfileDataSource(
        remoteProfileDataSource: RemoteProfileDataSource
    ): ProfileDataSource

    @LocalDataSource
    @Binds
    abstract fun provideLocalCartDataSource(
        localCartDataSource: LocalCartDataSource
    ): CartDataSource
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteDataSource


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalDataSource