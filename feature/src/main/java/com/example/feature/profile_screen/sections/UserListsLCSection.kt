package com.example.feature.profile_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.core.data.models.user_models.user_lists_response.Result
import com.example.core.design_system.list_card.ListCard

@Composable
fun UserListsLCSection(
    userLists: LazyPagingItems<Result>,
    userName: String,
    avatarPath: String,
    gravatarPath: String
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 16.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            UserInfoHeader(
                avatarPath = avatarPath,
                username = userName,
                gravatarPath = gravatarPath
            )
        }

        items(userLists.itemCount) { index ->
            val list = userLists[index]

            list?.let {
                ListCard(
                    moviesAmount = list.itemCount.toString(),
                    posterPath = "https://image.tmdb.org/t/p/w500/${list.posterPath}",
                    title = list.name,
                    description = list.description,
                    index = index
                ) {

                }
            }
        }
    }
}