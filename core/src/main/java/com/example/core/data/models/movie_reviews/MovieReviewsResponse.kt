package com.example.core.data.models.movie_reviews

import com.google.gson.annotations.SerializedName

data class MovieReviewsResponse(
    val id: Int,
    val page: Int,
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)