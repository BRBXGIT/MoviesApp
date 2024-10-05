package com.example.core.domain

import androidx.paging.PagingData
import com.example.core.data.local.TMDBUser
import com.example.core.data.models.list_models.create_list_models.create_list_request.CreateListRequest
import com.example.core.data.models.list_models.create_list_models.create_list_response.CreateListResponse
import com.example.core.data.models.list_models.delete_list_models.DeleteListResponse
import com.example.core.data.models.user_models.account_details_response.AccountDetailsResponse
import com.example.core.data.models.user_models.user_lists_response.Result
import kotlinx.coroutines.flow.Flow

interface ProfileScreenRepo {

    suspend fun getAccountDetails(sessionId: String): AccountDetailsResponse

    fun getUser(): Flow<List<TMDBUser>>

    fun getUserLists(accountId: Int, sessionId: String): Flow<PagingData<Result>>

    suspend fun createList(sessionId: String, createListRequest: CreateListRequest): CreateListResponse

    suspend fun deleteList(listId: Int, sessionId: String): DeleteListResponse
}