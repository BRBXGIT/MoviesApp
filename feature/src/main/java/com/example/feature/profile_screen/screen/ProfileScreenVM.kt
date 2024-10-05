package com.example.feature.profile_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.core.common.Dispatcher
import com.example.core.common.MoviesAppDispatchers
import com.example.core.data.models.list_models.create_list_models.create_list_request.CreateListRequest
import com.example.core.data.models.user_models.account_details_response.AccountDetailsResponse
import com.example.core.data.repos.ProfileScreenRepoImpl
import com.example.core.design_system.snackbars.SnackbarAction
import com.example.core.design_system.snackbars.SnackbarController
import com.example.core.design_system.snackbars.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenVM @Inject constructor(
    private val repository: ProfileScreenRepoImpl,
    @Dispatcher(MoviesAppDispatchers.Io) private val dispatcherIo: CoroutineDispatcher
): ViewModel() {

    private val _userDetails = MutableStateFlow<AccountDetailsResponse?>(null)
    val userDetails = _userDetails.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        null
    )

    private val _error = MutableStateFlow(false)
    val error = _error.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        false
    )

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        ""
    )

    fun setUserDetails() {
        viewModelScope.launch(dispatcherIo) {
            _error.value = false
            try {
                repository.getUser().collect { userData ->
                    _userDetails.value = repository.getAccountDetails(userData[0].sessionId)
                }
            } catch(e: Exception) {
                _error.value = true
                _errorMessage.value = e.message.toString()
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = "Internet exception, try with vpn :)",
                        action = SnackbarAction(
                            name = "Refresh",
                            action = {
                                setUserDetails()
                            }
                        )
                    )
                )
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val userLists = repository.getUser()
        .filterNotNull()
        .flatMapLatest { userData ->
            repository.getUserLists(userData[0].userId, userData[0].sessionId).cachedIn(viewModelScope)
        }

    fun createList(listData: CreateListRequest) {
        viewModelScope.launch(dispatcherIo) {
            try {
                repository.getUser().collect { userData ->
                    val response = repository.createList(userData[0].sessionId, listData)
                    if(response.success) {
                        SnackbarController.sendEvent(
                            SnackbarEvent(
                                message = "Created, you can pull to update :)"
                            )
                        )
                    } else {
                        SnackbarController.sendEvent(
                            SnackbarEvent(
                                message = "Something went wrong",
                            )
                        )
                    }
                }
            } catch(e: Exception) {
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = "Internet exception, try with vpn :)",
                    )
                )
            }
        }
    }

    fun deleteList(listId: Int) {
        viewModelScope.launch(dispatcherIo) {
            try {
                repository.getUser().collect { userData ->
                    val response = repository.deleteList(listId, userData[0].sessionId)

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
                        message = "Something wrong with internet, try with vpn :)"
                    )
                )
            }
        }
    }
}