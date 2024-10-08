package com.example.feature.favorites_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import com.example.core.data.models.movie_models.movies_genres_response.Genre
import com.example.core.data.models.movie_models.movies_previews_response.Result
import com.example.core.design_system.movie_card.MovieCard
import com.example.feature.movie_screen.navigation.MovieScreenRoute

@Composable
fun FavoriteMoviesLCSection(
    movies: LazyPagingItems<Result>,
    genres: List<Genre>,
    navController: NavHostController,
    onMovieDelete: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 16.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(movies.itemCount) { index ->
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
                    onMovieClick = {
                        navController.navigate(
                            MovieScreenRoute(
                                movieId = movie.id
                            )
                        )
                    },
                    movieInList = true,
                    onDeleteButtonClick = {
                        onMovieDelete(movie.id)
                    }
                )
            }
        }
    }
}