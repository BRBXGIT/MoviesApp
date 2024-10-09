package com.example.feature.settings_screen.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.feature.settings_screen.screen.SettingsScreen
import kotlinx.serialization.Serializable

@Serializable
object SettingsScreenRoute

fun NavGraphBuilder.settingsScreen(
    mainScaffoldPadding: PaddingValues
) = composable<SettingsScreenRoute> {
    SettingsScreen(mainScaffoldPadding = mainScaffoldPadding)
}