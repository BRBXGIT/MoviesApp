package com.example.core.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.data.models.list_models.list_details_models.Item
import com.example.core.utils.Utils
import retrofit2.HttpException
import java.io.IOException

class ListDetailsPagingSource(
    private val apiInstance: TMDBApiInstance,
    private val listId: Int
): PagingSource<Int, Item>() {
    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        val startPage = params.key ?: 1
        val accessToken = Utils.ACCESS_TOKEN

        return try {
            val movies = apiInstance.getListDetails(accessToken, listId, startPage)
            val nextPage = if(movies.items.isEmpty()) null else movies.page + 1

            LoadResult.Page(
                data = movies.items,
                prevKey = if(startPage == 1) null else startPage - 1,
                nextKey = nextPage
            )

        } catch(e: IOException) {
            LoadResult.Error(e)
        } catch(e: HttpException) {
            LoadResult.Error(e)
        }
    }
}