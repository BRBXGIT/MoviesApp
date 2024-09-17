package com.example.feature.movie_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.ui.theme.mColors

@Composable
fun MovieScreen(
    movieId: Int,
    mainScaffoldPadding: PaddingValues,
    viewModel: MovieScreenVM
) {
    val movieDetails = viewModel.movieDetails.collectAsStateWithLifecycle().value
    LaunchedEffect(movieDetails == null) {
        viewModel.setMovieDetails(movieId)
    }

    if(movieDetails != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(mColors.background)
                .padding(mainScaffoldPadding)
        ) {

        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(mColors.background),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}