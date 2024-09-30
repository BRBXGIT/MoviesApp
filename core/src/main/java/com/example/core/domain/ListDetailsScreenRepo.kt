package com.example.core.domain

import androidx.paging.PagingData
import com.example.core.data.models.list_details_models.Item
import kotlinx.coroutines.flow.Flow

interface ListDetailsScreenRepo {

    suspend fun getListDetails(listId: Int): Flow<PagingData<Item>>
}