package com.example.feature.profile_screen.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core.design_system.error_section.ErrorSection
import com.example.core.design_system.snackbars.SnackbarController
import com.example.core.design_system.snackbars.SnackbarEvent
import com.example.core.ui.theme.mColors
import com.example.feature.R
import com.example.feature.profile_screen.sections.UserListsLCSection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    mainScaffoldPadding: PaddingValues,
    viewModel: ProfileScreenVM,
    scope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController
) {
    val user = viewModel.userDetails.collectAsStateWithLifecycle().value
    val userLists = viewModel.userLists.collectAsLazyPagingItems()
    LaunchedEffect(user) {
        if(user == null) {
            viewModel.setUserDetails()
        }
    }
    val userError = viewModel.error.collectAsStateWithLifecycle().value
    val userErrorMessage = viewModel.errorMessage.collectAsStateWithLifecycle().value

    var listsError by rememberSaveable { mutableStateOf(false) }
    var listsErrorMessage by rememberSaveable { mutableStateOf("") }
    if(user != null) {
        if(userLists.loadState.refresh is LoadState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(mColors.background)
                    .padding(mainScaffoldPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            var isRefreshing by rememberSaveable { mutableStateOf(false) }

            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = {
                    scope.launch {
                        isRefreshing = true
                        delay(2000)
                        userLists.refresh()
                        isRefreshing = false
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .background(mColors.background)
                    .padding(mainScaffoldPadding)
            ) {
                UserListsLCSection(
                    userName = user.username,
                    userLists = userLists,
                    avatarPath = "https://image.tmdb.org/t/p/w500/${user.avatar.tmdb.avatarPath}",
                    gravatarPath = "https://www.gravatar.com/avatar/${user.avatar.gravatar.hash}?s=500&d=identicon",
                    navController = navController,
                    onDeleteListClick = {
                        scope.launch {
                            viewModel.deleteList(it, userLists)
                        }
                    },
                    listsError = listsError,
                    listsErrorMessage = listsErrorMessage
                )
            }
        }
    } else if(userError) {
        ErrorSection(
            animation = R.raw.profile_error_animation,
            errorMessage = userErrorMessage
        )
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

    LaunchedEffect(userLists.loadState) {
        if(userLists.loadState.refresh is LoadState.Error) {
            listsErrorMessage = (userLists.loadState.refresh as LoadState.Error).error.message.toString()
            listsError = true
            SnackbarController.sendEvent(
                SnackbarEvent(
                    message = "Internet exception, try with vpn :)"
                )
            )
        }
    }
}