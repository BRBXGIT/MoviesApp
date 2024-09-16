package com.example.core.design_system.top_bar

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.core.design_system.movies_app_icons.MoviesAppIcons
import com.example.core.ui.theme.mColors
import com.example.core.ui.theme.mTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTopAppBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    CenterAlignedTopAppBar(
        title = { Text(
            text = title,
            style = mTypography.titleMedium
        ) },
        actions = {
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    painter = painterResource(
                        id = if(title == "") MoviesAppIcons.Heart else MoviesAppIcons.Settings
                    ),
                    contentDescription = null
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    painter = painterResource(
                        id = if(title == "") MoviesAppIcons.ArrowLeft else MoviesAppIcons.Magnifier
                    ),
                    contentDescription = null
                )
            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = mColors.background)
    )
}