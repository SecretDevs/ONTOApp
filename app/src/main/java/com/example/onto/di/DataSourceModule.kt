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
    @Binds
    abstract fun bindRemoteProductsDataSource(
        remoteProductsDataSource: RemoteProductsDataSourceImpl
    ): RemoteProductsDataSource

    @Binds
    abstract fun bindLocalProductsDataSource(
        localProductsDataSource: LocalProductsDataSourceImpl
    ): LocalProductsDataSource

    @RemoteDataSource
    @Binds
    abstract fun bindRemoteProfileDataSource(
        remoteProfileDataSource: RemoteProfileDataSource
    ): ProfileDataSource

    @LocalDataSource
    @Binds
    abstract fun bindLocalCartDataSource(
        localCartDataSource: LocalCartDataSource
    ): CartDataSource
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteDataSource


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalDataSource