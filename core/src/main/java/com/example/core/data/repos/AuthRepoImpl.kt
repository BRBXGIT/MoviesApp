package com.example.core.data.repos

import com.example.core.data.models.request_token_response.RequestTokenResponse
import com.example.core.data.remote.TMDBApiInstance
import com.example.core.domain.AuthRepo
import com.example.core.utils.Utils
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(
    private val apiInstance: TMDBApiInstance
): AuthRepo {

    private val accessToken = Utils.ACCESS_TOKEN

    override suspend fun getRequestToken(): RequestTokenResponse {
        return apiInstance.getRequestToken(accessToken)
    }
}