package com.example.onto.data.local

import android.content.Context
import androidx.room.*
import com.example.onto.vo.OntoShop

@Database(entities = [OntoShop::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun shopDao(): ShopDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "shops")
                .fallbackToDestructiveMigration()
                .build()
    }

}