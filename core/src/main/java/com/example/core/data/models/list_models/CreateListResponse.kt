package com.example.core.data.models.list_models

import com.google.gson.annotations.SerializedName

data class CreateListResponse(
    @SerializedName("list_id")
    val listId: Int,
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("status_message")
    val statusMessage: String,
    val success: Boolean
)