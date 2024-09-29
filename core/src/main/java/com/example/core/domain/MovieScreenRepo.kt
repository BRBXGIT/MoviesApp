package com.example.core.domain

import androidx.paging.PagingData
import com.example.core.data.models.movie_models.movie_details_response.MovieDetailsResponse
import com.example.core.data.models.movie_models.movie_reviews_response.Result
import com.example.core.data.models.movie_models.movie_videos_response.MovieVideosResponse
import kotlinx.coroutines.flow.Flow

interface MovieScreenRepo {

    suspend fun getMovieDetails(movieId: Int): MovieDetailsResponse

    fun getMovieReviews(movieId: Int): Flow<PagingData<Result>>

    suspend fun getMovieVideos(movieId: Int): MovieVideosResponse
}