package com.example.core.domain

import androidx.paging.PagingData
import com.example.core.data.local.TMDBUser
import com.example.core.data.models.add_favorite_models.AddRemoveFavoriteRequest
import com.example.core.data.models.add_favorite_models.AddRemoveFavoriteResponse
import com.example.core.data.models.movie_models.movies_genres_response.MoviesGenresResponse
import com.example.core.data.models.movie_models.movies_previews_response.Result
import kotlinx.coroutines.flow.Flow

interface UserFavoritesScreenRepo {

    fun getUserFavorites(accountId: Int): Flow<PagingData<Result>>

    fun getUserData(): Flow<List<TMDBUser>>

    suspend fun getAllMoviesGenres(): MoviesGenresResponse

    suspend fun addRemoveMovieToFavorite(
        accountId: Int,
        sessionId: String,
        addRemoveFavoriteRequest: AddRemoveFavoriteRequest
    ): AddRemoveFavoriteResponse
}