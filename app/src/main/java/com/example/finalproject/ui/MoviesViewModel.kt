package com.example.finalproject.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.domain.use_cases.GetAllMoviesUseCase
import com.example.finalproject.data.utils.ExceptionResource
import com.example.finalproject.data.utils.Resources
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
) : ViewModel() {

    private val _state = MutableSharedFlow<UiState>()
    val state = _state.asSharedFlow()

    private val _movies = MutableStateFlow<UiModel>(UiModel.Loading)
    val movies: StateFlow<UiModel>
        get() = _movies

    init {
        getAllMovies()
    }

    private fun getAllMovies() {
        viewModelScope.launch {
            getAllMoviesUseCase().collect { result ->
                when (result) {
                    is Resources.Success -> {
                        _movies.emit(UiModel.MoviesModel(result.data?: emptyList()))
                    }
                    is Resources.Loading -> {
                        _movies.emit(UiModel.Loading)
                    }
                    is Resources.Error -> {
                        when (result.exception) {
                            is ExceptionResource.InvalidLogin -> {
                                _state.emit(UiState.InvalidLogin)
                            }
                            is ExceptionResource.IoException -> {
                                _state.emit(UiState.IoException(error = result.exception.error))
                            }
                            is ExceptionResource.NetworkError -> {
                                _state.emit(UiState.NetworkError)
                            }
                            else -> {}
                        }
                    }
                }
            }
        }

    }


}