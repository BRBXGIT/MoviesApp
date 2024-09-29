package com.example.core.data.repos

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.data.models.movie_models.movies_previews_response.Result
import com.example.core.data.models.movie_models.movies_genres_response.MoviesGenresResponse
import com.example.core.data.remote.LatestMoviesPagingSource
import com.example.core.data.remote.TMDBApiInstance
import com.example.core.domain.LatestMoviesScreenRepo
import com.example.core.utils.Utils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LatestMoviesScreenRepoImpl @Inject constructor(
    private val apiInstance: TMDBApiInstance
): LatestMoviesScreenRepo {

    //Latest movies
    override fun getLatestMovies(): Flow<PagingData<Result>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { LatestMoviesPagingSource(apiInstance) }
        ).flow
    }

    //All genres
    private val accessToken = Utils.ACCESS_TOKEN
    override suspend fun getAllMoviesGenres(): MoviesGenresResponse {
        return apiInstance.getAllMoviesGenres(accessToken)
    }
}