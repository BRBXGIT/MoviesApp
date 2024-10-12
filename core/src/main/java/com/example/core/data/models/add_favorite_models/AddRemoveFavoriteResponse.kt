package com.example.core.data.models.add_favorite_models

import com.google.gson.annotations.SerializedName

data class AddRemoveFavoriteResponse(
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("status_message")
    val statusMessage: String,
    val success: Boolean
)