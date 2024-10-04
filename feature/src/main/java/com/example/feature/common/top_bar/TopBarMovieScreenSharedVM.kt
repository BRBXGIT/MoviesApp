package com.example.feature.common.top_bar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.core.common.Dispatcher
import com.example.core.common.MoviesAppDispatchers
import com.example.core.data.models.add_favorite_models.add_favorite_request.AddRemoveFavoriteRequest
import com.example.core.data.models.add_movie_to_list_models.AddRemoveMovieToListRequest
import com.example.core.data.repos.MovieScreenRepoImpl
import com.example.core.design_system.snackbars.SnackbarController
import com.example.core.design_system.snackbars.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopBarMovieScreenSharedVM @Inject constructor(
    private val repository: MovieScreenRepoImpl,
    @Dispatcher(MoviesAppDispatchers.Io) private val dispatcherIo: CoroutineDispatcher
): ViewModel() {

    private var movieId: Int = 0
    fun setMovieId(id: Int) {
        movieId = id
    }

    fun addMovieToFavorite() {
        viewModelScope.launch(dispatcherIo) {
            try {
                repository.getUserData().collect { userData ->
                    val response = repository.addRemoveMovieToFavorite(
                        userData[0].userId,
                        userData[0].sessionId,
                        AddRemoveFavoriteRequest(
                            mediaId = movieId,
                            favorite = true
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

    fun addMovieToList(listId: Int) {
        viewModelScope.launch(dispatcherIo) {
            try {
                repository.getUserData().collect { userData ->
                    val response = repository.addMovieToList(
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

    @OptIn(ExperimentalCoroutinesApi::class)
    val userLists = repository.getUserData()
        .filterNotNull()
        .flatMapLatest { userData ->
            repository.getUserLists(userData[0].userId, userData[0].sessionId).cachedIn(viewModelScope)
        }
}