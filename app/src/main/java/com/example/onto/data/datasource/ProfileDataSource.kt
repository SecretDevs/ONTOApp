package com.example.onto.data.datasource

import com.example.onto.data.remote.OntoApiService
import com.example.onto.utils.Result
import com.example.onto.vo.remote.OntoUser
import javax.inject.Inject

interface ProfileDataSource {
    suspend fun getUserById(userId: Long): Result<OntoUser>
}

class RemoteProfileDataSource @Inject constructor(
    private val service: OntoApiService
) : ProfileDataSource {

    override suspend fun getUserById(userId: Long): Result<OntoUser> =
        try {
            val response = service.getUserById()
            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!.data)
            } else {
                Result.Error(Throwable(response.message()))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }

}