package com.example.feature.auth_screen.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.feature.auth_screen.screen.AuthScreen
import com.example.feature.auth_screen.screen.AuthScreenVM
import kotlinx.serialization.Serializable

@Serializable
object AuthScreenRoute

fun NavGraphBuilder.authScreen(
    mainScaffoldPadding: PaddingValues
) = composable<AuthScreenRoute> {
    val authScreenVM = hiltViewModel<AuthScreenVM>()

    AuthScreen(
        mainScaffoldPadding = mainScaffoldPadding,
        viewModel = authScreenVM
    )
}