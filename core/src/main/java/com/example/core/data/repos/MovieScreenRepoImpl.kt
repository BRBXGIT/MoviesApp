package com.example.core.data.repos

import com.example.core.data.models.movie_detail.MovieDetails
import com.example.core.data.remote.TMDBApiInstance
import com.example.core.domain.MovieScreenRepo
import com.example.core.utils.Utils
import javax.inject.Inject

class MovieScreenRepoImpl @Inject constructor(
    private val apiInstance: TMDBApiInstance
): MovieScreenRepo {

    private val accessToken = Utils.ACCESS_TOKEN

    override suspend fun getMovieDetails(movieId: Int): MovieDetails {
        return apiInstance.getMovieDetails(accessToken, movieId)
    }
}