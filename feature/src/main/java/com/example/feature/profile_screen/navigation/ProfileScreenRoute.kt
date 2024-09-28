package com.example.feature.profile_screen.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.feature.profile_screen.screen.ProfileScreen
import com.example.feature.profile_screen.screen.ProfileScreenVM
import kotlinx.serialization.Serializable

@Serializable
object ProfileScreenRoute

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.profileScreen(
    mainScaffoldPadding: PaddingValues,
    scrollBehavior: TopAppBarScrollBehavior
) = composable<ProfileScreenRoute> {
    val profileScreenVM = hiltViewModel<ProfileScreenVM>()

    ProfileScreen(
        mainScaffoldPadding = mainScaffoldPadding,
        viewModel = profileScreenVM,
        scrollBehavior = scrollBehavior
    )
}