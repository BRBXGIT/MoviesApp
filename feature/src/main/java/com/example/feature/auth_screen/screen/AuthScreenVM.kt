package com.example.feature.auth_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.common.Dispatcher
import com.example.core.common.MoviesAppDispatchers
import com.example.core.data.models.session_request.SessionRequest
import com.example.core.data.repos.AuthRepoImpl
import com.example.core.design_system.snackbars.SnackbarController
import com.example.core.design_system.snackbars.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthScreenVM @Inject constructor(
    private val repository: AuthRepoImpl,
    @Dispatcher(MoviesAppDispatchers.Io) private val dispatcherIo: CoroutineDispatcher
): ViewModel() {

    private val _requestToken = MutableStateFlow("")
    val requestToken = _requestToken.asStateFlow()

    fun setRequestToken() {
        viewModelScope.launch(dispatcherIo) {
            try {
                val response = repository.getRequestToken()
                if(response.success) {
                    _requestToken.value = response.request_token
                } else {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = "Something went wrong :("
                        )
                    )
                }
            } catch(e: Exception) {
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = "Something went wrong :("
                    )
                )
            }
        }
    }

    fun getSessionId() {
        viewModelScope.launch(dispatcherIo) {
            try {
                val response = repository.createSession(SessionRequest(requestToken = _requestToken.value))

                if(response.success) {

                } else {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = "Something went wrong :("
                        )
                    )
                }
            } catch(e: Exception) {
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = "Something went wrong :("
                    )
                )
            }
        }
    }
}