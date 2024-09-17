package com.example.feature.movie_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.ui.theme.mColors
import com.example.core.ui.theme.mTypography

@Composable
fun DescriptionSection(
    description: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Description",
            style = mTypography.titleMedium.copy(
                color = mColors.onSecondaryContainer
            )
        )

        Text(
            text = description,
            style = mTypography.bodyMedium.copy(
                color = mColors.secondary
            )
        )
    }
}