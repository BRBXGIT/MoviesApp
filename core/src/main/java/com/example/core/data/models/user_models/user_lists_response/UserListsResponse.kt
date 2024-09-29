package com.example.core.data.models.user_models.user_lists_response

import com.google.gson.annotations.SerializedName

data class UserListsResponse(
    val page: Int,
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)