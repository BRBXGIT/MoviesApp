package com.example.feature.auth_screen.screen

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.core.ui.theme.mColors
import com.example.core.ui.theme.mShapes
import com.example.feature.latest_movies_screen.navigation.LatestMoviesScreenRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    mainScaffoldPadding: PaddingValues,
    viewModel: AuthScreenVM,
    context: Context = LocalContext.current,
    navController: NavHostController,
    prefs: SharedPreferences,
    scope: CoroutineScope = rememberCoroutineScope()
) {
    val localUserData by viewModel.localUserData.collectAsState(initial = emptyList())
    LaunchedEffect(localUserData) {
        if(localUserData.isNotEmpty()) {
            prefs.edit().apply {
                putBoolean("authenticated", true)
                apply()
            }
            navController.navigate(LatestMoviesScreenRoute)
        }
    }

    var isRefreshing by rememberSaveable { mutableStateOf(false) }
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { /*TODO*/ },
        modifier = Modifier
            .fillMaxSize()
            .background(mColors.background)
            .padding(
                bottom = mainScaffoldPadding.calculateBottomPadding() + 16.dp,
                top = mainScaffoldPadding.calculateTopPadding(),
                start = 16.dp,
                end = 16.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        val requestToken by viewModel.requestToken.collectAsState()

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Button(
                onClick = {
                    scope.launch {
                        isRefreshing = true
                        delay(1000)
                        viewModel.setRequestToken()
                        isRefreshing = false
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = mShapes.small
            ) {
                Text(text = "Get token")
            }

            AnimatedVisibility(visible = (requestToken != "")) {
                Button(
                    onClick = {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://www.themoviedb.org/authenticate/$requestToken")
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = mShapes.small
                ) {
                    Text(text = "Authenticate")
                }
            }
        }

        AnimatedVisibility(
            visible = (requestToken != ""),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Button(
                onClick = {
                    scope.launch {
                        isRefreshing = true
                        delay(1000)
                        viewModel.setSessionId()
                        isRefreshing = false
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                shape = mShapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff518242)
                )
            ) {
                Text(text = "I authenticated")
            }
        }
    }
}