package com.example.core.data.models.add_movie_to_list_models

import com.google.gson.annotations.SerializedName

data class AddRemoveMovieToListRequest(
    @SerializedName("media_id")
    val mediaId: Int
)