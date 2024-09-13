package com.example.core.domain

import androidx.paging.PagingData
import com.example.core.data.models.latest_movies_response.Result
import com.example.core.data.models.movies_genres_response.MoviesGenresResponse
import kotlinx.coroutines.flow.Flow

interface MoviesScreenRepo {

    fun getLatestMovies(): Flow<PagingData<Result>>

    suspend fun getAllMoviesGenres(): MoviesGenresResponse
}