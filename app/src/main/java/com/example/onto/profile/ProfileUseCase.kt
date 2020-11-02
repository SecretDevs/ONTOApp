package com.example.onto.profile

import com.example.onto.utils.Result
import com.example.onto.vo.OntoUser

interface ProfileUseCase {
    suspend fun getUserProfile(userId: Long): Result<OntoUser>
}

class ProfileUseCaseImpl(
    private val remoteProfileDataSource: ProfileDataSource
) : ProfileUseCase {

    override suspend fun getUserProfile(userId: Long): Result<OntoUser> =
        remoteProfileDataSource.getUserById(userId)

}