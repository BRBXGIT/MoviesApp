package com.example.core.data.remote

import com.example.core.data.models.latest_movies_response.LatestMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface TMDBApiInstance {

    @GET("trending/all/day")
    suspend fun getTrendingMovies(
        @Query("page") page: Int,
        @Header("Authorization") accessToken: String
    ): LatestMoviesResponse
}