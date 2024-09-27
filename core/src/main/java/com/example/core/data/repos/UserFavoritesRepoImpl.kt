package com.example.core.data.repos

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.data.models.movies_previews_response.Result
import com.example.core.data.remote.TMDBApiInstance
import com.example.core.data.remote.UserFavoritesPagingSource
import com.example.core.domain.UserFavoritesScreenRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserFavoritesRepoImpl @Inject constructor(
    private val apiInstance: TMDBApiInstance
): UserFavoritesScreenRepo {

    override fun getUserFavorites(accountId: Int): Flow<PagingData<Result>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { UserFavoritesPagingSource(apiInstance, accountId) }
        ).flow
    }
}