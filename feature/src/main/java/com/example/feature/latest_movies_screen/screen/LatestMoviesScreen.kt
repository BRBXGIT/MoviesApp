package com.example.feature.latest_movies_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.core.ui.theme.mColors
import com.example.core.ui.theme.mTypography
import com.example.feature.R
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

    var error by rememberSaveable { mutableStateOf(false) }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(mColors.background)
            .padding(mainScaffoldPadding)
    ) {
        if(latestMovies.loadState.refresh is LoadState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(mColors.background),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if(error) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.another))
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(mColors.background)
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LottieAnimation(
                        composition = composition,
                        modifier = Modifier.size(170.dp),
                        iterations = LottieConstants.IterateForever
                    )

                    Text(
                        text = errorMessage,
                        style = mTypography.bodyMedium.copy(
                            lineBreak = LineBreak.Paragraph
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LatestMoviesLCSection(
                movies = latestMovies,
                genres = moviesGenres
            )
        }

        LaunchedEffect(latestMovies.loadState) {
            if(latestMovies.loadState.refresh is LoadState.Error) {
                error = true
                errorMessage = (latestMovies.loadState.refresh as LoadState.Error).error.message.toString()
                SnackbarController.sendEvent(SnackbarEvent(
                    message = "Internet exception, try with vpn :)",
                ))
            }
        }
    }
}