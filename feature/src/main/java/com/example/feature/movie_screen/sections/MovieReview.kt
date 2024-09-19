package com.example.feature.movie_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.core.data.models.movie_reviews_response.Result
import com.example.core.design_system.animated_shimmer.AnimatedShimmer
import com.example.core.design_system.movies_app_icons.MoviesAppIcons
import com.example.core.ui.theme.mColors
import com.example.core.ui.theme.mShapes
import com.example.core.ui.theme.mTypography
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun MovieReview(
    review: Result,
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        if(review.authorDetails.avatarPath == null) {
            Icon(
                painter = painterResource(id = MoviesAppIcons.User),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        } else {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/w500/${review.authorDetails.avatarPath}")
                    .crossfade(500)
                    .size(Size.ORIGINAL)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .clip(mShapes.small),
                filterQuality = FilterQuality.Low,
                contentScale = ContentScale.Crop,
                loading = { AnimatedShimmer() }
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = review.author,
                    style = mTypography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                val inputFormatter = DateTimeFormatter.ISO_DATE_TIME
                val outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
                val parsedDateTime = ZonedDateTime.parse(review.createdAt, inputFormatter)
                Text(
                    text = parsedDateTime.format(outputFormatter),
                    style = mTypography.labelSmall.copy(
                        color = mColors.primary
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(
                text = review.content,
                style = mTypography.bodySmall.copy(
                    color = mColors.secondary
                )
            )
        }
    }
}