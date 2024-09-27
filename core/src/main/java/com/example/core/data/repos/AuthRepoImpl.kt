package com.example.core.data.repos

import com.example.core.data.local.TMDBUser
import com.example.core.data.local.TMDBUserDao
import com.example.core.data.models.account_details_response.AccountDetailsResponse
import com.example.core.data.models.request_token_response.RequestTokenResponse
import com.example.core.data.models.session_request.SessionRequest
import com.example.core.data.models.session_response.SessionResponse
import com.example.core.data.remote.TMDBApiInstance
import com.example.core.domain.AuthRepo
import com.example.core.utils.Utils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(
    private val apiInstance: TMDBApiInstance,
    private val tmdbUserDao: TMDBUserDao
): AuthRepo {

    private val accessToken = Utils.ACCESS_TOKEN

    override suspend fun getRequestToken(): RequestTokenResponse {
        return apiInstance.getRequestToken(accessToken)
    }

    override suspend fun createSession(sessionRequest: SessionRequest): SessionResponse {
        return apiInstance.createSession(accessToken, sessionRequest)
    }

    override suspend fun getAccountDetails(sessionId: String): AccountDetailsResponse {
        return apiInstance.getAccountDetails(accessToken, sessionId)
    }

    override suspend fun upsertTMDBUser(tmdbUser: TMDBUser) {
        tmdbUserDao.upsertUser(tmdbUser)
    }

    override fun getLocalUserData(): Flow<List<TMDBUser>> {
        return tmdbUserDao.getUser()
    }
}