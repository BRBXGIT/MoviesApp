package com.example.feature.list_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import com.example.core.data.models.list_models.Item
import com.example.core.data.models.movie_models.movies_genres_response.Genre
import com.example.core.design_system.error_section.ErrorSection
import com.example.core.design_system.movie_card.MovieCard
import com.example.feature.R
import com.example.feature.movie_screen.navigation.MovieScreenRoute

@Composable
fun ListMoviesLCSection(
    movies: LazyPagingItems<Item>,
    genres: List<Genre>,
    navController: NavHostController,
    onDeleteButtonClick: (Int) -> Unit
) {
    if(movies.itemCount == 0) {
        ErrorSection(
            animation = R.raw.empty_list_animation,
            errorMessage = "Empty list :)"
        )
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 16.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(movies.itemCount, key = { it }) { index ->
                val movie = movies[index]

                movie?.let {
                    val movieGenres = genres.filter {
                        it.id in movie.genreIds
                    }

                    MovieCard(
                        rating = movie.voteAverage.toString(),
                        posterPath = "https://image.tmdb.org/t/p/w200/${movie.posterPath}",
                        title = movie.title,
                        genres = movieGenres.joinToString(separator = ", ") { it.name },
                        index = index,
                        movieInList = true,
                        onMovieClick = {
                            navController.navigate(
                                MovieScreenRoute(movie.id)
                            )
                        },
                        onDeleteButtonClick = {
                            onDeleteButtonClick(movie.id)
                        }
                    )
                }
            }
        }
    }
}