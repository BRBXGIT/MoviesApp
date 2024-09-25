package com.example.core.domain

import com.example.core.data.models.request_token_response.RequestTokenResponse

interface AuthRepo {

    suspend fun getRequestToken(): RequestTokenResponse
}