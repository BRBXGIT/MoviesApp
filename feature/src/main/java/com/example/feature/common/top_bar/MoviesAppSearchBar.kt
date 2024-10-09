package com.example.feature.common.top_bar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.core.data.models.movie_models.movies_genres_response.Genre
import com.example.core.data.models.movie_models.movies_previews_response.Result
import com.example.core.design_system.movies_app_icons.MoviesAppIcons
import com.example.core.ui.theme.mColors
import com.example.core.ui.theme.mTypography
import com.example.feature.movie_screen.navigation.MovieScreenRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesAppSearchBar(
    onExpandChange: () -> Unit,
    isSearching: Boolean,
    onSearch: (query: String) -> Unit,
    movies: LazyPagingItems<Result>,
    genres: List<Genre>,
    navController: NavHostController
) {
    var query by rememberSaveable { mutableStateOf("") }

    SearchBar(
        inputField = {
            TextField(
                value = query,
                onValueChange = { query = it },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                maxLines = 1,
                placeholder = {
                    Text(text = "Find movie")
                },
                leadingIcon = {
                    IconButton(
                        onClick = { onExpandChange() }
                    ) {
                        Icon(
                            painter = painterResource(id = MoviesAppIcons.ArrowLeft),
                            contentDescription = null
                        )
                    }
                },
                trailingIcon = {
                    if(query.isNotEmpty()) {
                        IconButton(
                            onClick = { query = "" }
                        ) {
                            Icon(
                                painter = painterResource(id = MoviesAppIcons.BackspaceFilled),
                                contentDescription = null
                            )
                        }
                    }
                },
                keyboardActions = KeyboardActions(
                    onSearch = { onSearch(query) }
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                )
            )
        },
        expanded = isSearching,
        onExpandedChange = { onExpandChange() },
    ) {
        if(movies.loadState.refresh is LoadState.Loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(
                    vertical = 16.dp,
                )
            ) {
                items(movies.itemCount) { index ->
                    val movie = movies[index]

                    movie?.let {
                        val movieGenres = genres.filter {
                            it.id in movie.genreIds
                        }
                        val genresString = movieGenres.take(2).joinToString(", ") { it.name }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onExpandChange()
                                    navController.navigate(
                                        MovieScreenRoute(
                                            movieId = movie.id
                                        )
                                    )
                                }
                                .padding(16.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = MoviesAppIcons.Magnifier),
                                contentDescription = null
                            )

                            Column(
                                verticalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(
                                    text = movie.title,
                                    style = mTypography.labelLarge
                                )

                                Text(
                                    text = "${movie.voteAverage.toString().take(3)} • $genresString",
                                    style = mTypography.labelMedium.copy(
                                        color = mColors.secondary
                                    )
                                )
                            }
                        }
                    }
                }

                item {
                    if(movies.loadState.refresh is LoadState.Loading) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}