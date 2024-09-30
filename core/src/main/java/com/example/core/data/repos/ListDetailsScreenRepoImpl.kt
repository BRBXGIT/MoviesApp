package com.example.core.data.repos

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.data.models.list_details_models.Item
import com.example.core.data.models.movie_models.movies_genres_response.MoviesGenresResponse
import com.example.core.data.remote.ListDetailsPagingSource
import com.example.core.data.remote.TMDBApiInstance
import com.example.core.domain.ListDetailsScreenRepo
import com.example.core.utils.Utils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListDetailsScreenRepoImpl @Inject constructor(
    private val apiInstance: TMDBApiInstance
): ListDetailsScreenRepo {

    //List details
    override fun getListDetails(listId: Int): Flow<PagingData<Item>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { ListDetailsPagingSource(apiInstance, listId) }
        ).flow
    }

    //All genres
    private val accessToken = Utils.ACCESS_TOKEN
    override suspend fun getAllMoviesGenres(): MoviesGenresResponse {
        return apiInstance.getAllMoviesGenres(accessToken)
    }
}