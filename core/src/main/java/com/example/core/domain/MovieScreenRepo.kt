package com.example.core.domain

import androidx.paging.PagingData
import com.example.core.data.models.movie_detail.MovieDetailsResponse
import com.example.core.data.models.movie_reviews.Result
import kotlinx.coroutines.flow.Flow

interface MovieScreenRepo {

    suspend fun getMovieDetails(movieId: Int): MovieDetailsResponse

    fun getMovieReviews(movieId: Int): Flow<PagingData<Result>>
}