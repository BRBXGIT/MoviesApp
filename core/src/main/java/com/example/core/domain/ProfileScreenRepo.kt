package com.example.core.domain

import com.example.core.data.local.TMDBUser
import com.example.core.data.models.account_details_response.AccountDetailsResponse
import kotlinx.coroutines.flow.Flow

interface ProfileScreenRepo {

    suspend fun getAccountDetailsById(accountId: Int): AccountDetailsResponse

    suspend fun getUser(): Flow<List<TMDBUser>>
}