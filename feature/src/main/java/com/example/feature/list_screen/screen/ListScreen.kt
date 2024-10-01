package com.example.feature.list_screen.screen

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
import com.example.core.design_system.error_section.ErrorSection
import com.example.core.design_system.snackbars.SnackbarAction
import com.example.core.design_system.snackbars.SnackbarController
import com.example.core.design_system.snackbars.SnackbarEvent
import com.example.core.ui.theme.mColors
import com.example.feature.R
import com.example.feature.list_screen.sections.ListMoviesLCSection

@Composable
fun ListScreen(
    mainScaffoldPadding: PaddingValues,
    viewModel: ListScreenVM,
    listId: Int,
    navController: NavHostController
) {
    viewModel.setListId(listId)
    val listMovies = viewModel.listMovies.collectAsLazyPagingItems()
    val moviesGenres by viewModel.allMoviesGenres.collectAsStateWithLifecycle()

    var error by rememberSaveable { mutableStateOf(false) }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(mColors.background)
            .padding(mainScaffoldPadding)
    ) {
        if(listMovies.loadState.refresh is LoadState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(mColors.background),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if(error) {
            ErrorSection(
                animation = R.raw.error_503_animation,
                errorMessage = errorMessage
            )
        } else {
            ListMoviesLCSection(
                movies = listMovies,
                genres = moviesGenres,
                navController = navController
            )
        }

        LaunchedEffect(listMovies.loadState) {
            if(listMovies.loadState.refresh is LoadState.Error) {
                error = true
                errorMessage = (listMovies.loadState.refresh as LoadState.Error).error.message.toString()
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = "Internet exception, try with vpn :)",
                        action = SnackbarAction(
                            name = "Refresh",
                            action = {
                                error = false
                                errorMessage = ""
                                listMovies.refresh()
                                viewModel.reloadGenres()
                            }
                        )
                    )
                )
            }
        }
    }
}