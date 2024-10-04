package com.example.core.design_system.movie_card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.core.design_system.animated_shimmer.AnimatedShimmer
import com.example.core.design_system.movies_app_icons.MoviesAppIcons
import com.example.core.ui.theme.mColors
import com.example.core.ui.theme.mShapes
import com.example.core.ui.theme.mTypography

@Composable
fun MovieCard(
    rating: String,
    posterPath: String,
    title: String,
    genres: String,
    index: Int,
    onMovieClick: () -> Unit,
    movieInList: Boolean = false,
    onDeleteButtonClick: () -> Unit = {}
) {
    Card(
        onClick = { onMovieClick() },
        shape = mShapes.small,
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(posterPath)
                    .crossfade(500)
                    .size(Size.ORIGINAL)
                    .build(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                filterQuality = FilterQuality.Low,
                contentScale = ContentScale.Crop,
                loading = { if(index < 8) AnimatedShimmer() }
            )

            if(movieInList) {
                Row(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .padding(6.dp)
                            .background(
                                color = mColors.primary,
                                shape = mShapes.small
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = rating.take(3),
                            style = mTypography.bodySmall.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(4.dp)
                        )
                    }

                    IconButton(
                        onClick = { onDeleteButtonClick() }
                    ) {
                        Icon(
                            painter = painterResource(id = MoviesAppIcons.TrashBin),
                            contentDescription = null
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .padding(6.dp)
                        .background(
                            color = mColors.primary,
                            shape = mShapes.small
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = rating.take(3),
                        style = mTypography.bodySmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(mColors.primaryContainer)
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = mTypography.titleSmall.copy(
                        fontWeight = FontWeight.Bold
                    )
                )


                Text(
                    text = genres,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = mTypography.bodySmall.copy(
                        color = mColors.secondary
                    )
                )
            }
        }
    }
}