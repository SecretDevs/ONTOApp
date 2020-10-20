package com.example.onto.maps

import com.example.onto.data.local.ShopDao
import com.example.onto.data.remote.OntoRemoteDataSource
import com.example.onto.di.AppModule
import com.example.onto.data.remote.OntoService
import com.example.onto.utils.performGetOperation
import com.example.onto.vo.OntoShop
import javax.inject.Inject

class MapsRepository @Inject constructor(
    private val remoteDataSource: OntoRemoteDataSource,
    private val localDataSource: ShopDao
) {

    fun getShops() = performGetOperation(
        databaseQuery = { localDataSource.getAllShops() },
        networkCall = { remoteDataSource.getShops() },
        saveCallResult = { localDataSource.insertAll(it.data) }
    )
}