package com.example.core.domain

import androidx.paging.PagingData
import com.example.core.data.models.movie_models.movies_previews_response.Result
import kotlinx.coroutines.flow.Flow

interface SearchScreenRepo {

    suspend fun searchScreenRepo(query: String): Flow<PagingData<Result>>
}