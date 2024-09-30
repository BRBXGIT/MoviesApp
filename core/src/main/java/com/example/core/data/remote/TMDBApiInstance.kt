package com.example.core.data.remote

import com.example.core.data.models.create_list_models.create_list_request.CreateListRequest
import com.example.core.data.models.create_list_models.create_list_response.CreateListResponse
import com.example.core.data.models.list_details_models.ListDetailsResponse
import com.example.core.data.models.movie_models.movie_details_response.MovieDetailsResponse
import com.example.core.data.models.movie_models.movie_reviews_response.MovieReviewsResponse
import com.example.core.data.models.movie_models.movie_videos_response.MovieVideosResponse
import com.example.core.data.models.movie_models.movies_genres_response.MoviesGenresResponse
import com.example.core.data.models.movie_models.movies_previews_response.MoviesPreviewsResponse
import com.example.core.data.models.session_models.request_token_response.RequestTokenResponse
import com.example.core.data.models.session_models.session_request.SessionRequest
import com.example.core.data.models.session_models.session_response.SessionResponse
import com.example.core.data.models.user_models.account_details_response.AccountDetailsResponse
import com.example.core.data.models.user_models.user_lists_response.UserListsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApiInstance {

    @GET("trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("page") page: Int,
        @Header("Authorization") accessToken: String
    ): MoviesPreviewsResponse

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

    @GET("authentication/token/new")
    suspend fun getRequestToken(
        @Header("Authorization") accessToken: String
    ): RequestTokenResponse

    @POST("authentication/session/new")
    suspend fun createSession(
        @Header("Authorization") accessToken: String,
        @Body sessionRequest: SessionRequest
    ): SessionResponse

    @GET("account")
    suspend fun getAccountDetails(
        @Header("Authorization") accessToken: String,
        @Query("session_id") sessionId: String
    ): AccountDetailsResponse

    @GET("account/{accountId}/favorite/movies")
    suspend fun getUserFavorites(
        @Header("Authorization") accessToken: String,
        @Path("accountId") accountId: Int,
        @Query("page") page: Int
    ): MoviesPreviewsResponse

    @GET("account/{accountId}/lists")
    suspend fun getUserLists(
        @Header("Authorization") accessToken: String,
        @Path("accountId") accountId: Int,
        @Query("page") page: Int,
        @Query("session_id") sessionId: String
    ): UserListsResponse

    @POST("list")
    suspend fun createList(
        @Header("Authorization") accessToken: String,
        @Query("session_id") sessionId: String,
        @Body createListRequest: CreateListRequest
    ): CreateListResponse

    @GET("list/{listId}")
    suspend fun getListDetails(
        @Header("Authorization") accessToken: String,
        @Path("listId") listId: Int,
        @Query("page") page: Int
    ): ListDetailsResponse
}