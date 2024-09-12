package com.example.core.data.models.latest_movies_response

import com.google.gson.annotations.SerializedName

data class LatestMoviesResponse(
    val page: Int,
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)