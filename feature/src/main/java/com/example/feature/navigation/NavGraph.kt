package com.example.feature.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.feature.common.snackbars.ObserveAsEvents
import com.example.feature.common.snackbars.SnackbarController
import com.example.feature.latest_movies_screen.navigation.LatestMoviesScreenRoute
import com.example.feature.latest_movies_screen.navigation.latestMoviesScreen
import kotlinx.coroutines.launch

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
                duration = SnackbarDuration.Short
            )

            if(result == SnackbarResult.ActionPerformed) {
                event.action?.action?.invoke()
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { mainScaffoldPadding ->
        NavHost(
            navController = navController,
            startDestination = LatestMoviesScreenRoute
        ) {
            latestMoviesScreen(mainScaffoldPadding)
        }
    }
}