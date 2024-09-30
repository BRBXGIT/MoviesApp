package com.example.feature.list_screen.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.feature.list_screen.screen.ListScreen
import com.example.feature.list_screen.screen.ListScreenVM
import kotlinx.serialization.Serializable

@Serializable
data class ListScreenRoute(
    val listId: Int
)

fun NavGraphBuilder.listScreen(
    mainScaffoldPadding: PaddingValues,
    navController: NavHostController
) = composable<ListScreenRoute> {
    val listId = it.toRoute<ListScreenRoute>().listId
    val listScreenVM = hiltViewModel<ListScreenVM>()

    ListScreen(
        mainScaffoldPadding = mainScaffoldPadding,
        viewModel = listScreenVM,
        listId = listId,
        navController = navController
    )
}