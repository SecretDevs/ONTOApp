package com.example.onto.di

import android.content.Context
import com.example.onto.data.local.AppDatabase
import com.example.onto.data.local.ShopDao
import com.example.onto.data.remote.OntoRemoteDataSource
import com.example.onto.data.remote.OntoService
import com.example.onto.maps.MapsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(
    ApplicationComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideOntoApiService(): OntoService {
        return Retrofit.Builder()
            .baseUrl("https://onto-ipc.herokuapp.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(OntoService::class.java)
    }

    @Provides
    fun provideOntoService(retrofit: Retrofit): OntoService = retrofit.create(OntoService::class.java)

    @Singleton
    @Provides
    fun provideOntoRemoteDataSource(ontoService: OntoService) = OntoRemoteDataSource(ontoService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideShopDao(db: AppDatabase) = db.shopDao()

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: OntoRemoteDataSource,
                          localDataSource: ShopDao) =
        MapsRepository(remoteDataSource, localDataSource)
}