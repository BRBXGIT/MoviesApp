package com.example.core.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.data.models.movie_models.movies_previews_response.Result
import com.example.core.utils.Utils
import retrofit2.HttpException
import java.io.IOException

class UserFavoritesPagingSource(
    private val apiInstance: TMDBApiInstance,
    private val accountId: Int
): PagingSource<Int, Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val startPage = params.key ?: 1
        val accessToken = Utils.ACCESS_TOKEN

        return try {
            val movies = apiInstance.getUserFavorites(accessToken, accountId, startPage)
            val nextPage = if(movies.results.isEmpty()) null else movies.page + 1

            LoadResult.Page(
                data = movies.results,
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