package com.example.feature.latest_movies_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.core.data.repos.MoviesScreenRepoImpl
import com.example.feature.common.snackbars.SnackbarController
import com.example.feature.common.snackbars.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LatestMoviesScreenVM @Inject constructor(
    private val repository: MoviesScreenRepoImpl
): ViewModel() {
    val latestMovies = repository.getLatestMovies().cachedIn(viewModelScope)

    val allMoviesGenres = flow {
        try {
            emit(repository.getAllMoviesGenres().genres)
        } catch(e: Exception) {
            SnackbarController.sendEvent(SnackbarEvent(
                message = "⚠ Internet exception, try with vpn :)"
            ))
            emit(emptyList())
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        emptyList()
    )
}