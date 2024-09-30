package com.example.core.data.models.add_favorite_models.add_favorite_request

import com.google.gson.annotations.SerializedName

data class AddFavoriteRequest(
    @SerializedName("media_type")
    val mediaType: String = "movie",
    @SerializedName("media_id")
    val mediaId: Int,
    val favorite: Boolean
)
