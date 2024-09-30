package com.example.core.domain

import androidx.paging.PagingData
import com.example.core.data.models.list_details_models.Item
import com.example.core.data.models.movie_models.movies_genres_response.MoviesGenresResponse
import kotlinx.coroutines.flow.Flow

interface ListDetailsScreenRepo {

    fun getListDetails(listId: Int): Flow<PagingData<Item>>

    suspend fun getAllMoviesGenres(): MoviesGenresResponse
}