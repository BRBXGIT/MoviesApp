package com.example.feature.settings_screen.sections

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.core.design_system.movies_app_icons.MoviesAppIcons

@Composable
fun LogoSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = MoviesAppIcons.AppIcon),
            contentDescription = null,
            modifier = Modifier.size(90.dp),
            tint = Color.Unspecified
        )
    }
}