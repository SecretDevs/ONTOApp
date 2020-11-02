package com.example.onto.di

import com.example.onto.data.remote.OntoApiService
import com.example.onto.products.ProductsDataSource
import com.example.onto.products.RemoteProductsDataSource
import com.example.onto.profile.ProfileDataSource
import com.example.onto.profile.RemoteProfileDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Qualifier

@Module
@InstallIn(
    ApplicationComponent::class
)
object DataSourceModule {

    @RemoteDataSource
    @Provides
    fun provideRemoteProductsDataSource(
        service: OntoApiService
    ): ProductsDataSource = RemoteProductsDataSource(service)

    @RemoteDataSource
    @Provides
    fun provideRemoteProfileDataSource(
        service: OntoApiService
    ): ProfileDataSource = RemoteProfileDataSource(service)

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteDataSource


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalDataSource