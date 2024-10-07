package com.example.core.data.repos

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.data.models.movie_models.movies_genres_response.MoviesGenresResponse
import com.example.core.data.models.movie_models.movies_previews_response.Result
import com.example.core.data.remote.SearchMoviesPagingSource
import com.example.core.data.remote.TMDBApiInstance
import com.example.core.domain.SearchScreenRepo
import com.example.core.utils.Utils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchScreenRepoImpl @Inject constructor(
    private val apiInstance: TMDBApiInstance
): SearchScreenRepo {

    private val accessToken = Utils.ACCESS_TOKEN

    override fun getMoviesByQuery(query: String): Flow<PagingData<Result>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { SearchMoviesPagingSource(apiInstance, query) }
        ).flow
    }

    override suspend fun getGenres(): MoviesGenresResponse {
        return apiInstance.getAllMoviesGenres(accessToken)
    }
}