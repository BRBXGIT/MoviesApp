package com.example.core.domain

import com.example.core.data.local.TMDBUser
import com.example.core.data.models.user_models.account_details_response.AccountDetailsResponse
import com.example.core.data.models.session_models.request_token_response.RequestTokenResponse
import com.example.core.data.models.session_models.session_request.SessionRequest
import com.example.core.data.models.session_models.session_response.SessionResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepo {

    suspend fun getRequestToken(): RequestTokenResponse

    suspend fun createSession(sessionRequest: SessionRequest): SessionResponse

    suspend fun getAccountDetails(sessionId: String): AccountDetailsResponse

    suspend fun upsertTMDBUser(tmdbUser: TMDBUser)

    fun getLocalUserData(): Flow<List<TMDBUser>>
}