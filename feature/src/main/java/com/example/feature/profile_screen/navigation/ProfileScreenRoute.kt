package com.example.feature.profile_screen.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.feature.profile_screen.screen.ProfileScreen
import kotlinx.serialization.Serializable

@Serializable
object ProfileScreenRoute

fun NavGraphBuilder.profileScreen(
    mainScaffoldPadding: PaddingValues
) = composable<ProfileScreenRoute> {
    ProfileScreen(mainScaffoldPadding = mainScaffoldPadding)
}