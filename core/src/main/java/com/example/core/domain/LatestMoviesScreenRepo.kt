package com.example.core.domain

import androidx.paging.PagingData
import com.example.core.data.models.movies_genres_response.MoviesGenresResponse
import com.example.core.data.models.movies_previews_response.Result
import kotlinx.coroutines.flow.Flow

interface LatestMoviesScreenRepo {

    fun getLatestMovies(): Flow<PagingData<Result>>

    suspend fun getAllMoviesGenres(): MoviesGenresResponse
}