package com.example.finalproject.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.finalproject.R
import com.example.finalproject.domain.use_cases.GetAllMoviesUseCase
import com.example.finalproject.utils.ExceptionResource
import com.example.finalproject.utils.Resources
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
    private val application: Application
) : AndroidViewModel(application) {

    private val _showError = MutableStateFlow<String>("")
    val showError = _showError.asStateFlow()

    private val _movies = MutableLiveData<UiState>()
    val movies: LiveData<UiState>
        get() = _movies

    init {
        getAllMovies()
    }

    private fun getAllMovies() {
        viewModelScope.launch {
            getAllMoviesUseCase().collect { result ->
                when (result) {
                    is Resources.Success -> {
                        _movies.map { UiState.UiModel(result.data ?: emptyList()) }
                    }

                    is Resources.Error -> {
                        when (result.exception) {
                            is ExceptionResource.InvalidLogin -> {
                                _movies.map { UiState.InvalidModel(application.getString(R.string.invalid_login)) }
                            }

                            is ExceptionResource.IoException -> {
                                _movies.map { UiState.InvalidModel(result.exception.error ?: "") }
                            }

                            is ExceptionResource.NetworkError -> {
                                _movies.map { UiState.InvalidModel(application.getString(R.string.network_error)) }
                            }

                            else -> {}
                        }
                    }
                }
            }
        }

    }


}