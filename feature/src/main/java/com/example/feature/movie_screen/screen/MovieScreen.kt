package com.example.feature.movie_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.ui.theme.mColors
import com.example.feature.movie_screen.sections.DescriptionSection
import com.example.feature.movie_screen.sections.HeaderSection
import com.example.feature.movie_screen.sections.ProductionCompaniesSection

@Composable
fun MovieScreen(
    movieId: Int,
    mainScaffoldPadding: PaddingValues,
    viewModel: MovieScreenVM,
) {
    val movieDetails = viewModel.movieDetails.collectAsStateWithLifecycle().value
    LaunchedEffect(movieDetails == null) {
        viewModel.setMovieDetails(movieId)
    }

    if(movieDetails != null) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(mColors.background)
                .padding(mainScaffoldPadding),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                HeaderSection(movieDetails = movieDetails)
            }

            item {
                DescriptionSection(description = movieDetails.overview)
            }

            item {
                ProductionCompaniesSection(productionCompanies = movieDetails.productionCompanies)
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(mColors.background)
                .padding(mainScaffoldPadding),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}