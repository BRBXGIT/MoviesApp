package com.example.core.data.remote

import com.example.core.data.models.latest_movies_response.LatestMoviesResponse
import com.example.core.data.models.movie_detail.MovieDetails
import com.example.core.data.models.movies_genres_response.MoviesGenresResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApiInstance {

    @GET("trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("page") page: Int,
        @Header("Authorization") accessToken: String
    ): LatestMoviesResponse

    @GET("genre/movie/list")
    suspend fun getAllMoviesGenres(
        @Header("Authorization") accessToken: String
    ): MoviesGenresResponse

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(
        @Header("Authorization") accessToken: String,
        @Path("movieId") movieId: Int
    ): MovieDetails
}