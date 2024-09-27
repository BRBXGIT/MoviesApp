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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core.design_system.snackbars.SnackbarAction
import com.example.core.design_system.snackbars.SnackbarController
import com.example.core.design_system.snackbars.SnackbarEvent
import com.example.core.ui.theme.mColors
import com.example.feature.favorites_screen.sections.FavoriteMoviesLCSection
import com.example.feature.favorites_screen.sections.FavoritesErrorSection

@Composable
fun FavoritesScreen(
    mainScaffoldPadding: PaddingValues,
    viewModel: FavoritesScreenVM,
    navController: NavHostController
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
        } else if(error) {
            FavoritesErrorSection(errorMessage = errorMessage)
        } else {
            FavoriteMoviesLCSection(
                movies = userFavorites,
                genres = moviesGenres,
                navController = navController
            )
        }

        LaunchedEffect(userFavorites.loadState) {
            if(userFavorites.loadState.refresh is LoadState.Error) {
                error = true
                errorMessage = (userFavorites.loadState.refresh as LoadState.Error).error.message.toString()
                SnackbarController.sendEvent(SnackbarEvent(
                    message = "Internet exception, try with vpn :)",
                    action = SnackbarAction(
                        name = "Refresh",
                        action = {
                            error = false
                            errorMessage = ""
                            userFavorites.refresh()
                            viewModel.reloadGenres()
                        }
                    )
                ))
            }
        }
    }
}