package com.example.feature.latest_movies_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core.ui.theme.mColors
import com.example.feature.common.snackbars.SnackbarController
import com.example.feature.common.snackbars.SnackbarEvent
import com.example.feature.latest_movies_screen.sections.LatestMoviesLCSection

@Composable
fun LatestMoviesScreen(
    mainScaffoldPadding: PaddingValues,
    viewModel: LatestMoviesScreenVM
) {
    val latestMovies = viewModel.latestMovies.collectAsLazyPagingItems()
    val moviesGenres by viewModel.allMoviesGenres.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(mColors.background)
            .padding(mainScaffoldPadding)
    ) {
        LatestMoviesLCSection(
            movies = latestMovies,
            genres = moviesGenres
        )

        LaunchedEffect(latestMovies.loadState) {
            if(latestMovies.loadState.refresh is LoadState.Error) {
                SnackbarController.sendEvent(SnackbarEvent(
                    message = "⚠ Something wrong with internet, try with vpn :)"
                ))
            }
        }
    }
}