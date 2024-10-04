package com.example.core.data.repos

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.data.local.TMDBUser
import com.example.core.data.local.TMDBUserDao
import com.example.core.data.models.add_favorite_models.add_favorite_request.AddRemoveFavoriteRequest
import com.example.core.data.models.add_favorite_models.add_favorite_response.AddRemoveFavoriteResponse
import com.example.core.data.models.movie_models.movies_genres_response.MoviesGenresResponse
import com.example.core.data.models.movie_models.movies_previews_response.Result
import com.example.core.data.remote.TMDBApiInstance
import com.example.core.data.remote.UserFavoritesPagingSource
import com.example.core.domain.UserFavoritesScreenRepo
import com.example.core.utils.Utils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserFavoritesRepoImpl @Inject constructor(
    private val apiInstance: TMDBApiInstance,
    private val tmdbUserDao: TMDBUserDao
): UserFavoritesScreenRepo {

    private val accessToken = Utils.ACCESS_TOKEN

    override fun getUserFavorites(accountId: Int): Flow<PagingData<Result>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { UserFavoritesPagingSource(apiInstance, accountId) }
        ).flow
    }

    override fun getUserData(): Flow<List<TMDBUser>> {
        return tmdbUserDao.getUser()
    }

    override suspend fun getAllMoviesGenres(): MoviesGenresResponse {
        return apiInstance.getAllMoviesGenres(accessToken)
    }

    override suspend fun addRemoveMovieToFavorite(
        accountId: Int,
        sessionId: String,
        addRemoveFavoriteRequest: AddRemoveFavoriteRequest
    ): AddRemoveFavoriteResponse {
        return apiInstance.addRemoveMovieToFavorite(accessToken, accountId, sessionId, addRemoveFavoriteRequest)
    }
}