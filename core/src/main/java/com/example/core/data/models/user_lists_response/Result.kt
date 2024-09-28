package com.example.core.data.models.user_lists_response

import com.google.gson.annotations.SerializedName

data class Result(
    val description: String,
    @SerializedName("favorite_count")
    val favoriteCount: Int,
    val id: Int,
    @SerializedName("iso_639_1")
    val iso6391: String,
    @SerializedName("item_count")
    val itemCount: Int,
    @SerializedName("list_type")
    val listType: String,
    val name: String,
    @SerializedName("poster_path")
    val posterPath: Any
)