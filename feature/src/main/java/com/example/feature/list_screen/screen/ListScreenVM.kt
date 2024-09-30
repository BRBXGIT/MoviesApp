package com.example.feature.list_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.core.data.repos.ListDetailsScreenRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ListScreenVM @Inject constructor(
    private val repository: ListDetailsScreenRepoImpl
): ViewModel() {

    private var listId: Int = 0
    fun setListId(id: Int) {
        listId = id
    }
    val listMovies by lazy {
        repository.getListDetails(listId).cachedIn(viewModelScope)
    }

    private val _reloadTrigger = MutableStateFlow(0)
    @OptIn(ExperimentalCoroutinesApi::class)
    val allMoviesGenres = _reloadTrigger.flatMapLatest {
        flow {
            try {
                emit(repository.getAllMoviesGenres().genres)
            } catch (e: Exception) {
                emit(emptyList())
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        emptyList()
    )

    //Increment value to reload genres
    fun reloadGenres() {
        _reloadTrigger.value++
    }
}