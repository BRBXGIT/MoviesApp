package com.example.core.domain

import androidx.paging.PagingData
import com.example.core.data.local.TMDBUser
import com.example.core.data.models.account_details_response.AccountDetailsResponse
import com.example.core.data.models.user_lists_response.Result
import kotlinx.coroutines.flow.Flow

interface ProfileScreenRepo {

    suspend fun getAccountDetails(sessionId: String): AccountDetailsResponse

    fun getUser(): Flow<List<TMDBUser>>

    fun getUserLists(accountId: Int, sessionId: String): Flow<PagingData<Result>>
}