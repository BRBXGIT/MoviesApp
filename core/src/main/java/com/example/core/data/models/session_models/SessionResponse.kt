package com.example.core.data.models.session_models

import com.google.gson.annotations.SerializedName

data class SessionResponse(
    val success: Boolean,
    @SerializedName("session_id")
    val sessionId: String
)
