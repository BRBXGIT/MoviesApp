package com.example.core.data.repos

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.data.models.movie_details_response.MovieDetailsResponse
import com.example.core.data.models.movie_reviews_response.Result
import com.example.core.data.models.movie_videos_response.MovieVideosResponse
import com.example.core.data.remote.MovieReviewsPagingSource
import com.example.core.data.remote.TMDBApiInstance
import com.example.core.domain.MovieScreenRepo
import com.example.core.utils.Utils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieScreenRepoImpl @Inject constructor(
    private val apiInstance: TMDBApiInstance
): MovieScreenRepo {

    private val accessToken = Utils.ACCESS_TOKEN

    override suspend fun getMovieDetails(movieId: Int): MovieDetailsResponse {
        return apiInstance.getMovieDetails(accessToken, movieId)
    }

    override fun getMovieReviews(movieId: Int): Flow<PagingData<Result>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { MovieReviewsPagingSource(apiInstance, movieId) }
        ).flow
    }

    override suspend fun getMovieVideos(movieId: Int): MovieVideosResponse {
        return apiInstance.getMovieVideos(accessToken, movieId)
    }
}