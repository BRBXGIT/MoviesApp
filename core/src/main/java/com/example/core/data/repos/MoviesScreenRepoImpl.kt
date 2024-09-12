package com.example.core.data.repos

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.data.models.latest_movies_response.Result
import com.example.core.data.remote.LatestMoviesPagingSource
import com.example.core.data.remote.TMDBApiInstance
import com.example.core.domain.MoviesScreenRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesScreenRepoImpl @Inject constructor(
    private val apiInstance: TMDBApiInstance
): MoviesScreenRepo {

    //Latest movies
    override suspend fun getLatestMovies(): Flow<PagingData<Result>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { LatestMoviesPagingSource(apiInstance) }
        ).flow
    }
}