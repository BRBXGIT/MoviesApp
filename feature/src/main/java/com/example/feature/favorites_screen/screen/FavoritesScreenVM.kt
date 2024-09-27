package com.example.feature.favorites_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.core.data.repos.UserFavoritesRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FavoritesScreenVM @Inject constructor(
    private val repository: UserFavoritesRepoImpl
): ViewModel() {

    private val user = repository.getUserData()

    @OptIn(ExperimentalCoroutinesApi::class)
    val userFavorites = user
        .filterNotNull()
        .flatMapLatest { userData ->
            repository.getUserFavorites(accountId = userData[0].userId).cachedIn(viewModelScope)
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