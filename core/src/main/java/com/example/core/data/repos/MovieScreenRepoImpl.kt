package com.example.core.data.repos

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.data.local.TMDBUser
import com.example.core.data.local.TMDBUserDao
import com.example.core.data.models.add_favorite_models.add_favorite_request.AddRemoveFavoriteRequest
import com.example.core.data.models.add_favorite_models.add_favorite_response.AddRemoveFavoriteResponse
import com.example.core.data.models.add_movie_to_list_models.AddMovieToListRequest
import com.example.core.data.models.add_movie_to_list_models.AddMovieToListResponse
import com.example.core.data.models.movie_models.movie_details_response.MovieDetailsResponse
import com.example.core.data.models.movie_models.movie_reviews_response.Result
import com.example.core.data.models.movie_models.movie_videos_response.MovieVideosResponse
import com.example.core.data.remote.MovieReviewsPagingSource
import com.example.core.data.remote.TMDBApiInstance
import com.example.core.data.remote.UserListsPagingSource
import com.example.core.domain.MovieScreenRepo
import com.example.core.utils.Utils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieScreenRepoImpl @Inject constructor(
    private val apiInstance: TMDBApiInstance,
    private val tmdbUserDao: TMDBUserDao
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

    override suspend fun addRemoveMovieToFavorite(
        accountId: Int,
        sessionId: String,
        addRemoveFavoriteRequest: AddRemoveFavoriteRequest
    ): AddRemoveFavoriteResponse {
        return apiInstance.addRemoveMovieToFavorite(accessToken, accountId, sessionId, addRemoveFavoriteRequest)
    }

    override fun getUserData(): Flow<List<TMDBUser>> {
        return tmdbUserDao.getUser()
    }

    override suspend fun addMovieToList(
        listId: Int,
        sessionId: String,
        addMovieToListRequest: AddMovieToListRequest
    ): AddMovieToListResponse {
        return apiInstance.addMovieToList(accessToken, listId, sessionId, addMovieToListRequest)
    }

    override fun getUserLists(accountId: Int, sessionId: String): Flow<PagingData<com.example.core.data.models.user_models.user_lists_response.Result>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { UserListsPagingSource(apiInstance, accountId, sessionId) }
        ).flow
    }
}