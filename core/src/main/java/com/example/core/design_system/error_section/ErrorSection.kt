package com.example.core.design_system.error_section

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.core.ui.theme.mColors
import com.example.core.ui.theme.mTypography

@Composable
fun ErrorSection(
    animation: Int,
    errorMessage: String
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animation))
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(mColors.background)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LottieAnimation(
                composition = composition,
                modifier = Modifier.size(170.dp),
                iterations = LottieConstants.IterateForever
            )

            Text(
                text = errorMessage,
                style = mTypography.bodyMedium.copy(
                    lineBreak = LineBreak.Paragraph
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}