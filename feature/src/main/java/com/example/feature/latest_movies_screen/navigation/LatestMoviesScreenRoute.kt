package com.example.feature.latest_movies_screen.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.feature.latest_movies_screen.screen.LatestMoviesScreen
import com.example.feature.latest_movies_screen.screen.LatestMoviesScreenVM
import kotlinx.serialization.Serializable

@Serializable
object LatestMoviesScreenRoute

fun NavGraphBuilder.latestMoviesScreen(
    mainScaffoldPadding: PaddingValues
) = composable<LatestMoviesScreenRoute> {
    val latestMoviesScreenVM = hiltViewModel<LatestMoviesScreenVM>()
    LatestMoviesScreen(
        mainScaffoldPadding = mainScaffoldPadding,
        viewModel = latestMoviesScreenVM
    )
}