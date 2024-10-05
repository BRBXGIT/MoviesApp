package com.example.core.data.repos

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.data.local.TMDBUser
import com.example.core.data.local.TMDBUserDao
import com.example.core.data.models.list_models.create_list_models.create_list_request.CreateListRequest
import com.example.core.data.models.list_models.create_list_models.create_list_response.CreateListResponse
import com.example.core.data.models.list_models.delete_list_models.DeleteListResponse
import com.example.core.data.models.user_models.account_details_response.AccountDetailsResponse
import com.example.core.data.models.user_models.user_lists_response.Result
import com.example.core.data.remote.TMDBApiInstance
import com.example.core.data.remote.UserListsPagingSource
import com.example.core.domain.ProfileScreenRepo
import com.example.core.utils.Utils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileScreenRepoImpl @Inject constructor(
    private val apiInstance: TMDBApiInstance,
    private val tmdbUserDao: TMDBUserDao
): ProfileScreenRepo {

    private val accessToken = Utils.ACCESS_TOKEN

    override suspend fun getAccountDetails(sessionId: String): AccountDetailsResponse {
        return apiInstance.getAccountDetails(accessToken, sessionId)
    }

    override fun getUser(): Flow<List<TMDBUser>> {
        return tmdbUserDao.getUser()
    }

    override fun getUserLists(accountId: Int, sessionId: String): Flow<PagingData<Result>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { UserListsPagingSource(apiInstance, accountId, sessionId) }
        ).flow
    }

    override suspend fun createList(
        sessionId: String,
        createListRequest: CreateListRequest
    ): CreateListResponse {
        return apiInstance.createList(accessToken, sessionId, createListRequest)
    }

    override suspend fun deleteList(listId: Int, sessionId: String): DeleteListResponse {
        return apiInstance.deleteList(listId, sessionId)
    }
}