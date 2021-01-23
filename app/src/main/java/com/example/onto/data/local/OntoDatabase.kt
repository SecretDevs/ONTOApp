package com.example.onto.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.onto.vo.local.LocalCartItem
import com.example.onto.vo.local.LocalOntoProduct
import com.example.onto.vo.local.ProductsInnerRef

@Database(
    entities = [LocalCartItem::class, LocalOntoProduct::class, ProductsInnerRef::class],
    version = 1
)
abstract class OntoDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun productsDao(): ProductsDao
    abstract fun similarProductsDao(): SimilarProductsDao
}