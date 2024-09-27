package com.example.core.domain

import androidx.paging.PagingData
import com.example.core.data.models.movies_previews_response.Result
import kotlinx.coroutines.flow.Flow

interface UserFavoritesScreenRepo {

    fun getUserFavorites(userId: Int): Flow<PagingData<Result>>
}