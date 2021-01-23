package com.example.onto.di

import android.content.Context
import androidx.room.Room
import com.example.onto.data.local.CartDao
import com.example.onto.data.local.OntoDatabase
import com.example.onto.data.local.ProductsDao
import com.example.onto.data.local.SimilarProductsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {
    //TODO: main thread database??
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): OntoDatabase =
        Room.databaseBuilder(
            context,
            OntoDatabase::class.java,
            "onto_local_cache"
        ).allowMainThreadQueries().build()

    @Provides
    @Singleton
    fun provideCartDao(db: OntoDatabase): CartDao = db.cartDao()

    @Provides
    @Singleton
    fun provideProductsDao(db: OntoDatabase): ProductsDao = db.productsDao()

    @Provides
    @Singleton
    fun provideSimilarProductsDao(db: OntoDatabase): SimilarProductsDao = db.similarProductsDao()

}