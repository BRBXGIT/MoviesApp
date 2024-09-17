package com.example.feature.movie_screen.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.core.data.models.movie_detail.MovieDetails
import com.example.core.ui.theme.mColors
import com.example.core.ui.theme.mShapes
import com.example.core.ui.theme.mTypography
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun HeaderSection(
    movieDetails: MovieDetails,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(0.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500/${movieDetails.posterPath}",
                contentDescription = null,
                modifier = Modifier
                    .weight(0.4f)
                    .clip(mShapes.medium)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(0.6f)
            ) {
                Text(
                    text = movieDetails.title,
                    style = mTypography.titleLarge
                )

                Box(
                    modifier = Modifier
                        .background(
                            color = mColors.primary,
                            shape = mShapes.small
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = movieDetails.voteAverage.toString().take(3),
                        style = mTypography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(4.dp)
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    val genres = movieDetails.genres.joinToString(", ") { it.name }
                    Text(
                        text = genres,
                        style = mTypography.bodySmall.copy(
                            color = mColors.secondary
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
                    val date = LocalDate.parse(movieDetails.releaseDate, inputFormatter)

                    val outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)
                    Text(
                        text = date.format(outputFormatter),
                        style = mTypography.bodySmall.copy(
                            color = mColors.secondary
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = movieDetails.status,
                        style = mTypography.bodySmall.copy(
                            color = mColors.secondary
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = movieDetails.spokenLanguages.joinToString(", ") { it.name },
                        style = mTypography.bodySmall.copy(
                            color = mColors.secondary
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = "Adult: ${movieDetails.adult}",
                        style = mTypography.bodySmall.copy(
                            color = mColors.secondary
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        HorizontalDivider(
            thickness = 1.dp
        )
    }
}