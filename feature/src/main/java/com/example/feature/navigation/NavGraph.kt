package com.example.feature.navigation

import android.content.SharedPreferences
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.core.design_system.snackbars.ObserveAsEvents
import com.example.core.design_system.snackbars.SnackbarController
import com.example.core.design_system.top_bar.CommonTopAppBar
import com.example.feature.auth_screen.navigation.AuthScreenRoute
import com.example.feature.auth_screen.navigation.authScreen
import com.example.feature.common.bottom_bar.CommonBottomBar
import com.example.feature.favorites_screen.navigation.favoritesScreen
import com.example.feature.latest_movies_screen.navigation.LatestMoviesScreenRoute
import com.example.feature.latest_movies_screen.navigation.latestMoviesScreen
import com.example.feature.list_screen.navigation.listScreen
import com.example.feature.movie_screen.navigation.movieScreen
import com.example.feature.profile_screen.create_list_bottom_sheet.CreateListBottomSheet
import com.example.feature.profile_screen.navigation.profileScreen
import com.example.feature.profile_screen.profile_screen_action_btn.ProfileScreenActionBtn
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    prefs: SharedPreferences
) {
    var createListBottomSheetOpen by rememberSaveable { mutableStateOf(false) }
    if(createListBottomSheetOpen) {
        CreateListBottomSheet(
            onDismissRequest = { createListBottomSheetOpen = false },
        )
    }

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
                withDismissAction = true
            )

            if(result == SnackbarResult.ActionPerformed) {
                event.action?.action?.invoke()
            }
        }
    }

    val mainTopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val currentDestination = if(currentRoute != null) currentRoute.toString().split(".")[5] else "MainScreenRoute"

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(mainTopAppBarScrollBehavior.nestedScrollConnection),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            if(currentDestination != "AuthScreenRoute") {
                CommonTopAppBar(
                    title = when(currentDestination) {
                        "LatestMoviesScreenRoute" -> "Latest movies"
                        "FavoritesScreenRoute" -> "Favorites"
                        "ProfileScreenRoute" -> "Profile"
                        else -> ""
                    },
                    scrollBehavior = mainTopAppBarScrollBehavior,
                    navController = navController
                )
            }
        },
        bottomBar = {
            CommonBottomBar(
                navController = navController,
                currentDestination = currentDestination
            )
        },
        floatingActionButton = {
            ProfileScreenActionBtn(
                currentDestination = currentDestination,
                onClick = { createListBottomSheetOpen = true }
            )
        },
    ) { mainScaffoldPadding ->
        NavHost(
            navController = navController,
            startDestination = if(!prefs.getBoolean("authenticated", false))
                AuthScreenRoute else LatestMoviesScreenRoute
        ) {
            latestMoviesScreen(
                mainScaffoldPadding = mainScaffoldPadding,
                navController = navController
            )

            favoritesScreen(
                mainScaffoldPadding = mainScaffoldPadding,
                navController = navController
            )

            profileScreen(
                mainScaffoldPadding = mainScaffoldPadding,
                navController = navController
            )

            movieScreen(mainScaffoldPadding)

            authScreen(
                mainScaffoldPadding = mainScaffoldPadding,
                prefs = prefs,
                navController = navController
            )

            listScreen(
                mainScaffoldPadding = mainScaffoldPadding,
                navController = navController
            )
        }
    }
}