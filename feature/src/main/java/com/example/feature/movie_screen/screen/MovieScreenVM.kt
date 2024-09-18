package com.example.feature.movie_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.core.common.Dispatcher
import com.example.core.common.MoviesAppDispatchers
import com.example.core.data.models.movie_detail.MovieDetailsResponse
import com.example.core.data.models.movie_reviews.Result
import com.example.core.data.repos.MovieScreenRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieScreenVM @Inject constructor(
    private val repository: MovieScreenRepoImpl,
    @Dispatcher(MoviesAppDispatchers.Io) private val dispatcherIo: CoroutineDispatcher
): ViewModel() {

    private val _movieDetailsResponse = MutableStateFlow<MovieDetailsResponse?>(null)
    val movieDetails = _movieDetailsResponse.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        null
    )

    fun setMovieDetails(movieId: Int) {
        viewModelScope.launch(dispatcherIo) {
            try {
                _movieDetailsResponse.value = repository.getMovieDetails(movieId)
            } catch(e: Exception) {
                println(e.message)
            }
        }
    }

    fun getMovieReviews(movieId: Int): Flow<PagingData<Result>> {
        return repository.getMovieReviews(movieId).cachedIn(viewModelScope)
    }
}