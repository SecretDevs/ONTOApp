package com.example.onto.data.usecase

import com.example.onto.data.datasource.ProfileDataSource
import com.example.onto.di.RemoteDataSource
import com.example.onto.utils.Result
import com.example.onto.vo.remote.OntoUser
import javax.inject.Inject

interface ProfileUseCase {
    suspend fun getUserProfile(userId: Long): Result<OntoUser>
}

class ProfileUseCaseImpl @Inject constructor(
    @RemoteDataSource private val remoteProfileDataSource: ProfileDataSource
) : ProfileUseCase {

    override suspend fun getUserProfile(userId: Long): Result<OntoUser> =
        remoteProfileDataSource.getUserById(userId)

}