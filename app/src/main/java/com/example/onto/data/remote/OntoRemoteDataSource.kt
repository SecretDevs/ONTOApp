package com.example.onto.data.remote

import javax.inject.Inject

class OntoRemoteDataSource @Inject constructor(
    private val ontoService: OntoService
): BaseDataSource() {

    suspend fun getShops() = getResult { ontoService.getShops() }
}