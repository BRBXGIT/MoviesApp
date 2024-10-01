package com.example.feature.common.top_bar

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.core.design_system.movies_app_icons.MoviesAppIcons
import com.example.core.ui.theme.mColors
import com.example.core.ui.theme.mTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTopAppBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavHostController,
    sharedViewModel: TopBarMovieScreenSharedVM
) {
    var addMovieToListBSOpened by rememberSaveable { mutableStateOf(false) }
    if(addMovieToListBSOpened) {
        AddMovieToListBS(
            viewModel = sharedViewModel,
            onDismissRequest = { addMovieToListBSOpened = false }
        )
    }

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = mTypography.titleMedium
            )
        },
        actions = {
            var dropDownMenuOpen by rememberSaveable { mutableStateOf(false) }
            IconButton(
                onClick = {
                    if(title == "") {
                        dropDownMenuOpen = true
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

            TopBarDropDownMenu(
                onDismissRequest = { dropDownMenuOpen = false },
                expanded = dropDownMenuOpen,
                onAddFavoriteClick = {
                    sharedViewModel.addRemoveMovieToFavorite(true)
                    dropDownMenuOpen = false
                },
                onRemoveFavoriteClick = {
                    sharedViewModel.addRemoveMovieToFavorite(false)
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
                    if(title == "") {
                        navController.navigateUp()
                    }
                }
            ) {
                Icon(
                    painter = painterResource(
                        id = if(title == "") MoviesAppIcons.ArrowLeft else MoviesAppIcons.Magnifier
                    ),
                    contentDescription = null
                )
            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = mColors.background)
    )
}