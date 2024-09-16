package com.example.feature.movie_screen.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.feature.movie_screen.screen.MovieScreen
import kotlinx.serialization.Serializable

@Serializable
data class MovieScreenRoute(
    val movieId: Int
)

fun NavGraphBuilder.movieScreen(
    mainScaffoldPadding: PaddingValues
) = composable<MovieScreenRoute> {
    val movieId = it.toRoute<MovieScreenRoute>().movieId

    MovieScreen(
        movieId = movieId,
        mainScaffoldPadding = mainScaffoldPadding
    )
}