package com.example.feature.common.top_bar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core.design_system.movies_app_icons.MoviesAppIcons
import com.example.core.ui.theme.mShapes
import com.example.core.ui.theme.mTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMovieToListBS(
    viewModel: TopBarMovieScreenSharedVM,
    onDismissRequest: () -> Unit
) {
    val userLists = viewModel.userLists.collectAsLazyPagingItems()

    ModalBottomSheet(
        onDismissRequest = { onDismissRequest() },
        tonalElevation = 0.dp,
        shape = mShapes.small
    ) {
        if(userLists.loadState.refresh is LoadState.Loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    Text(
                        text = "Add to list",
                        style = mTypography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                items(userLists.itemCount) { index ->
                    val list = userLists[index]

                    list?.let {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(0.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        viewModel.addMovieToList(list.id)
                                        onDismissRequest()
                                    }
                            ) {
                                Text(
                                    text = list.name,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = mTypography.titleSmall,
                                    modifier = Modifier.padding(
                                        start = 16.dp,
                                        top = 16.dp,
                                        bottom = 16.dp,
                                        end = 46.dp
                                    )
                                )

                                Icon(
                                    painter = painterResource(id = MoviesAppIcons.ArrowRightRoundFilled),
                                    contentDescription = null,
                                    modifier = Modifier.padding(end = 16.dp)
                                )
                            }

                            HorizontalDivider(thickness = 1.dp)
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}