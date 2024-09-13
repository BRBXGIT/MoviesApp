package com.example.feature.latest_movies_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.core.data.models.movies_genres_response.Genre
import com.example.core.data.repos.MoviesScreenRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LatestMoviesScreenVM @Inject constructor(
    private val repository: MoviesScreenRepoImpl
): ViewModel() {
    val latestMovies = repository.getLatestMovies().cachedIn(viewModelScope)

    val allMoviesGenres = flow {
        emit(repository.getAllMoviesGenres().genres)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        emptyList()
    )
}