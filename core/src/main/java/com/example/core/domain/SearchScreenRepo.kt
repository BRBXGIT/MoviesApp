package com.example.core.domain

import androidx.paging.PagingData
import com.example.core.data.models.movie_models.movies_genres_response.MoviesGenresResponse
import com.example.core.data.models.movie_models.movies_previews_response.Result
import kotlinx.coroutines.flow.Flow

interface SearchScreenRepo {

    fun getMoviesByQuery(query: String): Flow<PagingData<Result>>

    suspend fun getGenres(): MoviesGenresResponse
}