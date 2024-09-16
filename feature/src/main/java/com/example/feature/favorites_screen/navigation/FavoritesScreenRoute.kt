package com.example.feature.favorites_screen.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.feature.favorites_screen.screen.FavoritesScreen
import kotlinx.serialization.Serializable

@Serializable
object FavoritesScreenRoute

fun NavGraphBuilder.favoritesScreen(
    mainScaffoldPadding: PaddingValues
) = composable<FavoritesScreenRoute> {
    FavoritesScreen(mainScaffoldPadding = mainScaffoldPadding)
}