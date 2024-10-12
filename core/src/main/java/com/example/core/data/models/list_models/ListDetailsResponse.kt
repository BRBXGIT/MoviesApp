package com.example.core.data.models.list_models

import com.google.gson.annotations.SerializedName

data class ListDetailsResponse(
    @SerializedName("created_by")
    val createdBy: String,
    val description: String,
    @SerializedName("favorite_count")
    val favoriteCount: Int,
    val id: Int,
    @SerializedName("iso_639_1")
    val iso6391: String,
    @SerializedName("item_count")
    val itemCount: Int,
    val items: List<Item>,
    val name: String,
    val page: Int,
    @SerializedName("poster_path")
    val posterPath: Any,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)