package com.example.core.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.data.models.user_lists_response.Result
import com.example.core.utils.Utils
import retrofit2.HttpException
import java.io.IOException

class UserListsPagingSource(
    private val apiInstance: TMDBApiInstance,
    private val accountId: Int,
    private val sessionId: String
): PagingSource<Int, Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val startPage = params.key ?: 1
        val accessToken = Utils.ACCESS_TOKEN

        return try {
            val lists = apiInstance.getUserLists(accessToken, accountId, startPage, sessionId)
            val nextPage = if(lists.results.isEmpty()) null else lists.page + 1

            LoadResult.Page(
                data = lists.results,
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