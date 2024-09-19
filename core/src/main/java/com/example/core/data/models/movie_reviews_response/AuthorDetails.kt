package com.example.core.data.models.movie_reviews_response

import com.google.gson.annotations.SerializedName

data class AuthorDetails(
    @SerializedName("avatar_path")
    val avatarPath: String?,
    val name: String,
    val rating: Int,
    val username: String
)