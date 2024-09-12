package com.example.core.domain

import androidx.paging.PagingData
import com.example.core.data.models.latest_movies_response.Result
import kotlinx.coroutines.flow.Flow

interface MoviesScreenRepo {

    suspend fun getLatestMovies(): Flow<PagingData<Result>>
}