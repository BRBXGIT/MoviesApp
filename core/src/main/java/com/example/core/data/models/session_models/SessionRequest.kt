package com.example.core.data.models.session_models

import com.google.gson.annotations.SerializedName

data class SessionRequest(
    @SerializedName("request_token")
    val requestToken: String
)