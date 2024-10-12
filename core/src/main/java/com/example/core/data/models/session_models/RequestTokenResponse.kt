package com.example.core.data.models.session_models

data class RequestTokenResponse(
    val expires_at: String,
    val request_token: String,
    val success: Boolean
)