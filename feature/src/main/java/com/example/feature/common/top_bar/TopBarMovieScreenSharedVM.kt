package com.example.feature.common.top_bar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.common.Dispatcher
import com.example.core.common.MoviesAppDispatchers
import com.example.core.data.models.add_favorite_models.add_favorite_request.AddFavoriteRequest
import com.example.core.data.repos.MovieScreenRepoImpl
import com.example.core.design_system.snackbars.SnackbarController
import com.example.core.design_system.snackbars.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
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
                    val response = repository.addMovieToFavorite(
                        userData[0].userId,
                        userData[0].sessionId,
                        AddFavoriteRequest(
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
}