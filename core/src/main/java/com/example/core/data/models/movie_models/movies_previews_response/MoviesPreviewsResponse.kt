package com.example.core.data.models.movie_models.movies_previews_response

import com.google.gson.annotations.SerializedName

data class MoviesPreviewsResponse(
    val page: Int,
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)