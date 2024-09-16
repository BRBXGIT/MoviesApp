package com.example.feature.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.feature.common.bottom_bar.CommonBottomBar
import com.example.core.design_system.snackbars.ObserveAsEvents
import com.example.core.design_system.snackbars.SnackbarController
import com.example.core.design_system.top_bar.CommonTopAppBar
import com.example.feature.favorites_screen.navigation.favoritesScreen
import com.example.feature.latest_movies_screen.navigation.LatestMoviesScreenRoute
import com.example.feature.latest_movies_screen.navigation.latestMoviesScreen
import com.example.feature.profile_screen.navigation.profileScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph() {
    val navController = rememberNavController()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    ObserveAsEvents(flow = SnackbarController.events, snackbarHostState) { event ->
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()

            val result = snackbarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.action?.name,
                duration = SnackbarDuration.Indefinite,
            )

            if(result == SnackbarResult.ActionPerformed) {
                event.action?.action?.invoke()
            }
        }
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CommonTopAppBar(
                title = "Latest movies",
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            CommonBottomBar(navController = navController)
        }
    ) { mainScaffoldPadding ->
        NavHost(
            navController = navController,
            startDestination = LatestMoviesScreenRoute
        ) {
            latestMoviesScreen(mainScaffoldPadding)

            favoritesScreen(mainScaffoldPadding)

            profileScreen(mainScaffoldPadding)
        }
    }
}