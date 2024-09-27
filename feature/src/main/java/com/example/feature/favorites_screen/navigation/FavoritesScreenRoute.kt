package com.example.feature.favorites_screen.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.feature.favorites_screen.screen.FavoritesScreen
import com.example.feature.favorites_screen.screen.FavoritesScreenVM
import kotlinx.serialization.Serializable

@Serializable
object FavoritesScreenRoute

fun NavGraphBuilder.favoritesScreen(
    mainScaffoldPadding: PaddingValues,
    navController: NavHostController
) = composable<FavoritesScreenRoute> {
    val favoritesScreenVM = hiltViewModel<FavoritesScreenVM>()

    FavoritesScreen(
        mainScaffoldPadding = mainScaffoldPadding,
        viewModel = favoritesScreenVM,
        navController = navController
    )
}