package com.example.core.data.remote

import com.example.core.data.models.latest_movies_response.LatestMoviesResponse
import com.example.core.data.models.movie_details_response.MovieDetailsResponse
import com.example.core.data.models.movie_reviews_response.MovieReviewsResponse
import com.example.core.data.models.movie_videos_response.MovieVideosResponse
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
    ): MovieDetailsResponse

    @GET("movie/{movieId}/reviews")
    suspend fun getMovieReviews(
        @Header("Authorization") accessToken: String,
        @Path("movieId") movieId: Int
    ): MovieReviewsResponse

    @GET("movie/{movieId}/videos")
    suspend fun getMovieVideos(
        @Header("Authorization") accessToken: String,
        @Path("movieId") movieId: Int
    ): MovieVideosResponse
}