package com.example.feature.movie_screen.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.feature.movie_screen.screen.MovieScreen
import com.example.feature.movie_screen.screen.MovieScreenVM
import kotlinx.serialization.Serializable

@Serializable
data class MovieScreenRoute(
    val movieId: Int
)

fun NavGraphBuilder.movieScreen(
    mainScaffoldPadding: PaddingValues
) = composable<MovieScreenRoute> {
    val movieId = it.toRoute<MovieScreenRoute>().movieId
    val movieScreenVM = hiltViewModel<MovieScreenVM>()

    MovieScreen(
        movieId = movieId,
        mainScaffoldPadding = mainScaffoldPadding,
        viewModel = movieScreenVM
    )
}