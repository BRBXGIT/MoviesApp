package com.example.feature.movie_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core.ui.theme.mColors
import com.example.core.ui.theme.mTypography
import com.example.feature.common.top_bar.TopBarMovieScreenSharedVM
import com.example.feature.movie_screen.sections.DescriptionSection
import com.example.feature.movie_screen.sections.HeaderSection
import com.example.feature.movie_screen.sections.MovieReview
import com.example.feature.movie_screen.sections.ProductionCompaniesSection
import com.example.feature.movie_screen.sections.TrailerSection

@Composable
fun MovieScreen(
    movieId: Int,
    mainScaffoldPadding: PaddingValues,
    viewModel: MovieScreenVM,
    sharedViewModel: TopBarMovieScreenSharedVM
) {
    sharedViewModel.setMovieId(movieId)
    viewModel.setMovieId(movieId)
    val movieReviews = viewModel.movieReviews.collectAsLazyPagingItems()

    val movieDetails = viewModel.movieDetails.collectAsStateWithLifecycle().value
    val movieVideos = viewModel.movieVideos.collectAsStateWithLifecycle().value
    LaunchedEffect(key1 = (movieDetails == null)) {
        viewModel.setMovieDetails(movieId)
    }
    LaunchedEffect(key1 = (movieVideos == null)) {
        viewModel.setMovieVideos(movieId)
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
                HeaderSection(movieDetailsResponse = movieDetails)
            }

            item {
                DescriptionSection(description = movieDetails.overview)
            }

            if(movieVideos != null) {
                val movieTrailers = movieVideos.results.filter { it.type == "Trailer" }
                if(movieTrailers.isNotEmpty()) {
                    val movieTrailerId = movieTrailers[0].key

                    item {
                        TrailerSection(trailerId = movieTrailerId)
                    }
                }
            }

            item {
                ProductionCompaniesSection(productionCompanies = movieDetails.productionCompanies)
            }

            if(movieReviews.itemCount != 0) {
                item {
                    Text(
                        text = "Reviews",
                        style = mTypography.titleMedium.copy(
                            color = mColors.onSecondaryContainer
                        ),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                items(movieReviews.itemCount, key = { it }) { index ->
                    val review = movieReviews[index]

                    review?.let {
                        MovieReview(
                            review = review,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .animateItem()
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(0.dp))
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