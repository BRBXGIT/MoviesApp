package com.example.core.data.models.movie_models.movie_reviews_response

import com.google.gson.annotations.SerializedName

data class Result(
    val author: String,
    @SerializedName("author_details")
    val authorDetails: AuthorDetails,
    val content: String,
    @SerializedName("created_at")
    val createdAt: String,
    val id: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val url: String
)