package com.example.core.domain

import com.example.core.data.models.movie_detail.MovieDetails

interface MovieScreenRepo {

    suspend fun getMovieDetails(movieId: Int): MovieDetails
}