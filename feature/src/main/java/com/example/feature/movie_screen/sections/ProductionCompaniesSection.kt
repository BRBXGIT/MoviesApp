package com.example.feature.movie_screen.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.core.data.models.movie_detail.ProductionCompany
import com.example.core.design_system.movies_app_icons.MoviesAppIcons
import com.example.core.ui.theme.mColors
import com.example.core.ui.theme.mShapes
import com.example.core.ui.theme.mTypography

@Composable
fun ProductionCompaniesSection(
    productionCompanies: List<ProductionCompany>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Text(
            text = "Production companies",
            style = mTypography.titleMedium.copy(
                color = mColors.onSecondaryContainer
            ),
            modifier = Modifier.padding(start = 16.dp)
        )

        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            modifier = Modifier.height(100.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(
                horizontal = 16.dp
            )
        ) {
            items(productionCompanies) { company ->
                ProductionCompanyCard(
                    image = company.logoPath,
                    name = company.name,
                    country = company.originCountry
                )
            }
        }
    }
}

@Composable
fun ProductionCompanyCard(
    image: String?,
    name: String,
    country: String
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if(image != null) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(
                            color = mColors.secondary,
                            shape = mShapes.small
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500/${image}",
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(mShapes.extraSmall),
                        contentScale = ContentScale.Crop
                    )
                }
            } else {
                Icon(
                    painter = painterResource(id = MoviesAppIcons.TV),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = name,
                    style = mTypography.bodyMedium
                )

                Text(
                    text = country,
                    style = mTypography.bodySmall.copy(
                        color = mColors.secondary
                    )
                )
            }
        }
    }
}