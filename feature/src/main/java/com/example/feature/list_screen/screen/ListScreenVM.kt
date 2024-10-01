package com.example.feature.list_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.core.common.Dispatcher
import com.example.core.common.MoviesAppDispatchers
import com.example.core.data.models.add_movie_to_list_models.AddRemoveMovieToListRequest
import com.example.core.data.repos.ListDetailsScreenRepoImpl
import com.example.core.design_system.snackbars.SnackbarController
import com.example.core.design_system.snackbars.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListScreenVM @Inject constructor(
    private val repository: ListDetailsScreenRepoImpl,
    @Dispatcher(MoviesAppDispatchers.Io) private val dispatcherIo: CoroutineDispatcher
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

    fun removeMovieFromList(movieId: Int) {
        viewModelScope.launch(dispatcherIo) {
            try {
                repository.getUserData().collect { userData ->
                    val response = repository.removeMovieToList(
                        listId = listId,
                        sessionId = userData[0].sessionId,
                        addRemoveMovieToListRequest = AddRemoveMovieToListRequest(
                            mediaId = movieId
                        )
                    )
                    if(response.success) {
                        SnackbarController.sendEvent(
                            SnackbarEvent(
                                message = "Success"
                            )
                        )
                    } else {
                        SnackbarController.sendEvent(
                            SnackbarEvent(
                                message = "Something went wrong :("
                            )
                        )
                    }
                }
            } catch(e: Exception) {
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = "Internet exception, try with vpn :)"
                    )
                )
            }
        }
    }
}