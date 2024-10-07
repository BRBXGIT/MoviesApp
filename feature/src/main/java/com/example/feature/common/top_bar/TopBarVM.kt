package com.example.feature.common.top_bar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.core.data.repos.SearchScreenRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TopBarVM @Inject constructor(
    private val repository: SearchScreenRepoImpl
): ViewModel() {

    private var searchQuery: String = ""
    fun setQuery(query: String) {
        searchQuery = query
    }

    val allMoviesGenres = flow {
            try {
                emit(repository.getGenres().genres)
            } catch (e: Exception) {
                emit(emptyList())
            }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        emptyList()
    )

    val moviesByQuery = repository.getMoviesByQuery("The Platform 2").cachedIn(viewModelScope)
}