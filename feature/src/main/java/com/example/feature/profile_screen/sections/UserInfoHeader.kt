package com.example.feature.profile_screen.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.core.design_system.animated_shimmer.AnimatedShimmer
import com.example.core.ui.theme.mColors
import com.example.core.ui.theme.mTypography

@Composable
fun UserInfoHeader(
    avatarPath: String?,
    username: String,
    gravatarPath: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(mColors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape),
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(avatarPath ?: gravatarPath)
                    .crossfade(500)
                    .size(Size.ORIGINAL)
                    .build(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                filterQuality = FilterQuality.Low,
                contentScale = ContentScale.Crop,
                loading = { AnimatedShimmer() }
            )
        }

        Text(
            text = username,
            style = mTypography.titleMedium
        )
    }
}