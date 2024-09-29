package com.example.feature.profile_screen.profile_screen_action_btn

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.core.design_system.movies_app_icons.MoviesAppIcons

@Composable
fun ProfileScreenActionBtn(
    currentDestination: String,
    onClick: () -> Unit
) {
    AnimatedVisibility(
        visible = currentDestination == "ProfileScreenRoute",
        enter = slideInVertically(tween(300)) + fadeIn(tween(300)),
        exit = slideOutVertically(tween(300)) + fadeOut(tween(300))
    ) {
        ExtendedFloatingActionButton(
            text = {
                Text(text = "Create list")
            },
            icon = {
                Icon(
                    painter = painterResource(id = MoviesAppIcons.AddFilled),
                    contentDescription = null
                )
            },
            onClick = {
                onClick()
            }
        )
    }
}