package com.example.finalproject.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.finalproject.R
import com.example.finalproject.domain.models.MoviesModel
import com.example.finalproject.domain.use_cases.GetAllMoviesUseCase
import com.example.finalproject.utils.ExceptionResource
import com.example.finalproject.utils.Resources
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
    private val application: Application
) : AndroidViewModel(application) {

    private val _errorEvent = MutableSharedFlow<UiState>()
    val errorEvent = _errorEvent.asSharedFlow()

    private val _movies = MutableStateFlow<List<MoviesModel>>(emptyList())
    val movies: StateFlow<List<MoviesModel>>
        get() = _movies

    init {
        getAllMovies()
    }

    private fun getAllMovies() {
        viewModelScope.launch {
            getAllMoviesUseCase().collect { result ->
                when (result) {
                    is Resources.Success -> {
                        _movies.emit(result.data?: emptyList())
                    }

                    is Resources.Error -> {
                        when (result.exception) {
                            is ExceptionResource.InvalidLogin -> {
                                _errorEvent.emit(UiState.InvalidLogin)
                            }
                            is ExceptionResource.IoException -> {
                                _errorEvent.emit(UiState.IoException(error = result.exception.error))
                            }
                            is ExceptionResource.NetworkError -> {
                                _errorEvent.emit(UiState.NetworkError)
                            }
                            else -> {}
                        }
                    }
                }
            }
        }

    }


}