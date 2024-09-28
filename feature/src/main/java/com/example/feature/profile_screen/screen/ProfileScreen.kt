package com.example.feature.profile_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core.ui.theme.mColors
import com.example.feature.profile_screen.sections.UserListsLCSection

@Composable
fun ProfileScreen(
    mainScaffoldPadding: PaddingValues,
    viewModel: ProfileScreenVM,
) {
    val user = viewModel.userDetails.collectAsStateWithLifecycle().value
    LaunchedEffect(user) {
        if(user == null) {
            viewModel.setUserDetails()
        }
    }

    if(user != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(mColors.background)
                .padding(mainScaffoldPadding)
        ) {
            val userLists = viewModel.userLists.collectAsLazyPagingItems()
            UserListsLCSection(
                userName = user.username,
                userLists = userLists,
                avatarPath = "https://image.tmdb.org/t/p/w500/${user.avatar.tmdb.avatarPath}",
                gravatarPath = "https://www.gravatar.com/avatar/${user.avatar.gravatar.hash}?s=500&d=identicon"
            )
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(mColors.background)
                .padding(mainScaffoldPadding),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}