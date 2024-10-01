package com.example.core.domain

import androidx.paging.PagingData
import com.example.core.data.local.TMDBUser
import com.example.core.data.models.add_favorite_models.add_favorite_request.AddRemoveFavoriteRequest
import com.example.core.data.models.add_favorite_models.add_favorite_response.AddRemoveFavoriteResponse
import com.example.core.data.models.add_movie_to_list_models.AddRemoveMovieToListRequest
import com.example.core.data.models.add_movie_to_list_models.AddRemoveMovieToListResponse
import com.example.core.data.models.movie_models.movie_details_response.MovieDetailsResponse
import com.example.core.data.models.movie_models.movie_reviews_response.Result
import com.example.core.data.models.movie_models.movie_videos_response.MovieVideosResponse
import kotlinx.coroutines.flow.Flow

interface MovieScreenRepo {

    suspend fun getMovieDetails(movieId: Int): MovieDetailsResponse

    fun getMovieReviews(movieId: Int): Flow<PagingData<Result>>

    suspend fun getMovieVideos(movieId: Int): MovieVideosResponse

    suspend fun addRemoveMovieToFavorite(
        accountId: Int,
        sessionId: String,
        addRemoveFavoriteRequest: AddRemoveFavoriteRequest
    ): AddRemoveFavoriteResponse

    fun getUserData(): Flow<List<TMDBUser>>

    suspend fun addMovieToList(
        listId: Int,
        sessionId: String,
        addRemoveMovieToListRequest: AddRemoveMovieToListRequest
    ): AddRemoveMovieToListResponse

    fun getUserLists(accountId: Int, sessionId: String): Flow<PagingData<com.example.core.data.models.user_models.user_lists_response.Result>>
}