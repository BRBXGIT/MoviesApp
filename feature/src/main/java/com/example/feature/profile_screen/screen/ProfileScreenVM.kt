package com.example.feature.profile_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.core.common.Dispatcher
import com.example.core.common.MoviesAppDispatchers
import com.example.core.data.models.account_details_response.AccountDetailsResponse
import com.example.core.data.repos.ProfileScreenRepoImpl
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

    fun setUserDetails() {
        viewModelScope.launch(dispatcherIo) {
            try {
                repository.getUser().collect { userData ->
                    _userDetails.value = repository.getAccountDetails(userData[0].sessionId)
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
    val userLists = repository.getUser()
        .filterNotNull()
        .flatMapLatest { userData ->
            repository.getUserLists(userData[0].userId, userData[0].sessionId).cachedIn(viewModelScope)
        }
}