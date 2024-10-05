package com.example.feature.profile_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import com.example.core.data.models.user_models.user_lists_response.Result
import com.example.core.design_system.error_section.ErrorSection
import com.example.core.design_system.list_card.ListCard
import com.example.feature.R
import com.example.feature.list_screen.navigation.ListScreenRoute
import com.example.feature.profile_screen.screen.ProfileScreenVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun UserListsLCSection(
    userLists: LazyPagingItems<Result>,
    userName: String,
    avatarPath: String,
    gravatarPath: String,
    navController: NavHostController,
    viewModel: ProfileScreenVM,
    scope: CoroutineScope = rememberCoroutineScope()
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
                    index = index,
                    onListClick = {
                        navController.navigate(ListScreenRoute(list.id))
                    },
                    onDeleteListClick = {
                        scope.launch {
                            viewModel.deleteList(list.id)
                            delay(1000)
                            userLists.refresh()
                        }
                    }
                )
            }
        }

        if(userLists.itemCount == 0) {
            item {
                ErrorSection(
                    animation = R.raw.dont_have_lists_animation,
                    errorMessage = "You don't have list yet"
                )
            }
        }
    }
}