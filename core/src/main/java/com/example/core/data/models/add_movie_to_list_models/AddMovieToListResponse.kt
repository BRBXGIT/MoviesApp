package com.example.core.data.models.add_movie_to_list_models

import com.google.gson.annotations.SerializedName

data class AddMovieToListResponse(
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("status_message")
    val statusMessage: String,
    val success: Boolean
)
