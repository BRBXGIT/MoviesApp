package com.example.feature.common.top_bar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core.design_system.movies_app_icons.MoviesAppIcons
import com.example.core.ui.theme.mColors
import com.example.core.ui.theme.mTypography
import com.example.feature.settings_screen.navigation.SettingsScreenRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTopAppBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavHostController,
    sharedViewModel: TopBarMovieScreenSharedVM,
    topBarVM: TopBarVM
) {
    var addMovieToListBSOpened by rememberSaveable { mutableStateOf(false) }
    if(addMovieToListBSOpened) {
        AddMovieToListBS(
            viewModel = sharedViewModel,
            onDismissRequest = { addMovieToListBSOpened = false }
        )
    }

    var isSearching by rememberSaveable { mutableStateOf(false) }
    val moviesByQuery = topBarVM.moviesByQuery.collectAsLazyPagingItems()
    val moviesGenres = topBarVM.allMoviesGenres.collectAsStateWithLifecycle().value
    if(isSearching) {
        MoviesAppSearchBar(
            isSearching = true,
            onExpandChange = { isSearching = false },
            onSearch = {
                topBarVM.setQuery(it)
            },
            movies = moviesByQuery,
            genres = moviesGenres,
            navController = navController
        )
    }

    TopAppBar(
        title = {
            Text(
                text = title,
                style = mTypography.titleMedium
            )
        },
        actions = {
            var dropDownMenuOpen by rememberSaveable { mutableStateOf(false) }
            if(title != "Settings") {
                IconButton(
                    onClick = {
                        if(title == "") {
                            dropDownMenuOpen = true
                        } else {
                            navController.navigate(SettingsScreenRoute)
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(
                            id = if(title == "") MoviesAppIcons.DotsFilled else MoviesAppIcons.Settings
                        ),
                        contentDescription = null
                    )
                }
            }

            TopBarDropDownMenu(
                onDismissRequest = { dropDownMenuOpen = false },
                expanded = dropDownMenuOpen,
                onAddFavoriteClick = {
                    sharedViewModel.addMovieToFavorite()
                    dropDownMenuOpen = false
                },
                onAddToListClick = {
                    addMovieToListBSOpened = true
                    dropDownMenuOpen = false
                }
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    if((title == "") || (title == "List") || (title == "Settings")) {
                        navController.navigateUp()
                    } else {
                        isSearching = true
                    }
                }
            ) {
                Icon(
                    painter = painterResource(
                        id = if((title == "") || (title == "List") || (title == "Settings")) MoviesAppIcons.ArrowLeft else MoviesAppIcons.Magnifier
                    ),
                    contentDescription = null
                )
            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = mColors.background)
    )
}