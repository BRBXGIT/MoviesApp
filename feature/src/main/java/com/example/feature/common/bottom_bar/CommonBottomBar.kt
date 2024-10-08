package com.example.feature.common.bottom_bar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.core.design_system.movies_app_icons.MoviesAppIcons
import com.example.core.ui.theme.mTypography
import com.example.feature.favorites_screen.navigation.FavoritesScreenRoute
import com.example.feature.latest_movies_screen.navigation.LatestMoviesScreenRoute
import com.example.feature.profile_screen.navigation.ProfileScreenRoute

data class NavItem(
    val label: String,
    val defaultIcon: Int,
    val chosenIcon: Int,
    val destination: Any,
    val route: String
)

@Composable
fun CommonBottomBar(
    navController: NavHostController,
    currentDestination: String
) {
    val navItems = listOf(
        NavItem(
            label = "Movies",
            defaultIcon = MoviesAppIcons.TV,
            chosenIcon = MoviesAppIcons.TVFilled,
            destination = LatestMoviesScreenRoute,
            route = "LatestMoviesScreenRoute"
        ),
        NavItem(
            label = "Favorites",
            defaultIcon = MoviesAppIcons.Heart,
            chosenIcon = MoviesAppIcons.HeartFilled,
            destination = FavoritesScreenRoute,
            route = "FavoritesScreenRoute"
        ),
        NavItem(
            label = "Profile",
            defaultIcon = MoviesAppIcons.User,
            chosenIcon = MoviesAppIcons.UserFilled,
            destination = ProfileScreenRoute,
            route = "ProfileScreenRoute"
        )
    )

    val navBarRoutes = listOf(
        "LatestMoviesScreenRoute",
        "FavoritesScreenRoute",
        "ProfileScreenRoute"
    )

    AnimatedVisibility(
        visible = (currentDestination in navBarRoutes),
        enter = fadeIn(tween(500)),
        exit = fadeOut(tween(500))
    ) {
        NavigationBar(
            tonalElevation = 0.dp
        ) {
            navItems.forEach { navItem ->
                val chosen = navItem.route == currentDestination
                NavigationBarItem(
                    selected = chosen,
                    onClick = {
                        if(!chosen) {
                            navController.navigate(navItem.destination)
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = if(chosen) navItem.chosenIcon else navItem.defaultIcon),
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(
                            text = navItem.label,
                            style = mTypography.labelMedium
                        )
                    }
                )
            }
        }
    }
}