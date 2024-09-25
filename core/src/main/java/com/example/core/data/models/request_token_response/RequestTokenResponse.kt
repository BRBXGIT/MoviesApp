package com.example.core.data.models.request_token_response

data class RequestTokenResponse(
    val expires_at: String,
    val request_token: String,
    val success: Boolean
)