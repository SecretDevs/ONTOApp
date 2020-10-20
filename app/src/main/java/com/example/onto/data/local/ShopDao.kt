package com.example.onto.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.onto.vo.OntoShop

@Dao
interface ShopDao {

    @Query("SELECT * FROM shops")
    fun getAllShops() : LiveData<List<OntoShop>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<OntoShop>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: OntoShop)


}