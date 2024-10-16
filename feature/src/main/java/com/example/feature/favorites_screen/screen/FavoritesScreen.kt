package com.example.feature.favorites_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core.design_system.error_section.ErrorSection
import com.example.core.design_system.snackbars.SnackbarController
import com.example.core.design_system.snackbars.SnackbarEvent
import com.example.core.ui.theme.mColors
import com.example.feature.R
import com.example.feature.favorites_screen.sections.FavoriteMoviesLCSection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun FavoritesScreen(
    mainScaffoldPadding: PaddingValues,
    viewModel: FavoritesScreenVM,
    navController: NavHostController,
    scope: CoroutineScope = rememberCoroutineScope()
) {
    val userFavorites = viewModel.userFavorites.collectAsLazyPagingItems()
    val moviesGenres by viewModel.allMoviesGenres.collectAsStateWithLifecycle()

    var error by rememberSaveable { mutableStateOf(false) }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(mColors.background)
            .padding(mainScaffoldPadding)
    ) {
        if(userFavorites.loadState.refresh is LoadState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(mColors.background),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if(error || userFavorites.itemCount == 0) {
            ErrorSection(
                animation = R.raw.broken_heart_animation,
                errorMessage = if(error) errorMessage else "Nothing in favorites"
            )
        } else {
            FavoriteMoviesLCSection(
                movies = userFavorites,
                genres = moviesGenres,
                navController = navController,
                onMovieDelete = {
                    scope.launch {
                        viewModel.removeMovieFromFavorite(it, userFavorites)
                    }
                }
            )
        }

        LaunchedEffect(userFavorites.loadState) {
            if(userFavorites.loadState.refresh is LoadState.Error) {
                errorMessage = (userFavorites.loadState.refresh as LoadState.Error).error.message.toString()
                error = true
                SnackbarController.sendEvent(SnackbarEvent(
                    message = "Internet exception, try with vpn :)",
                ))
            }
        }
    }
}