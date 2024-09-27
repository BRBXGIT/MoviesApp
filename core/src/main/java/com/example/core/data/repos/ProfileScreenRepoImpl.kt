package com.example.core.data.repos

import com.example.core.data.local.TMDBUser
import com.example.core.data.local.TMDBUserDao
import com.example.core.data.models.account_details_response.AccountDetailsResponse
import com.example.core.data.remote.TMDBApiInstance
import com.example.core.domain.ProfileScreenRepo
import com.example.core.utils.Utils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileScreenRepoImpl @Inject constructor(
    private val apiInstance: TMDBApiInstance,
    private val tmdbUserDao: TMDBUserDao
): ProfileScreenRepo {

    private val accessToken = Utils.ACCESS_TOKEN

    override suspend fun getAccountDetailsById(accountId: Int): AccountDetailsResponse {
        return apiInstance.getAccountDetailsById(accessToken, accountId)
    }

    override suspend fun getUser(): Flow<List<TMDBUser>> {
        return tmdbUserDao.getUser()
    }
}