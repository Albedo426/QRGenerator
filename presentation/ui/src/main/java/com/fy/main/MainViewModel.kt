package com.fy.main

import androidx.lifecycle.viewModelScope
import com.fy.base.BaseViewModel
import com.fy.domain.movie.MovieUseCase
import com.fy.extension.network.onSuccess
import com.fy.utils.model.LoadingType
import com.fy.utils.model.MessageType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
) : BaseViewModel() {
    private val _state = MutableStateFlow<MovieUiState>(MovieUiState.Init)
    val movieUiState = _state.asStateFlow()

    fun getMovieList() {
        viewModelScope.launch {
            apiCall(
                loadingType = LoadingType.DEFAULT,
                successMessageType = MessageType.NONE,
                errorMessageType = MessageType.BOTTOM_SHEET,
                call = {
                    movieUseCase.invoke()
                }
            ).onSuccess { uiListModel ->
                uiListModel.let { movieList ->
                    _state.emit(MovieUiState.SuccessMovieList(movieList.toMutableList()))
                }
            }
        }
    }
}