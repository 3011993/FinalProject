package com.example.finalproject.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.R
import com.example.finalproject.domain.models.MoviesModel
import com.example.finalproject.domain.use_cases.GetAllMoviesUseCase
import com.example.finalproject.utils.FAILED_CONNECTION
import com.example.finalproject.utils.INVALID_LOGIN_ERROR
import com.example.finalproject.utils.NO_INTERNET_CONNECTION
import com.example.finalproject.utils.Resources
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Appendable

class MoviesViewModel(
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
    private val application: Application
) : AndroidViewModel(application) {

    private val _showError = MutableStateFlow<String>("")
    val showError = _showError.asStateFlow()

    private val _movies = MutableLiveData<List<MoviesModel>>()
    val movies: LiveData<List<MoviesModel>>
        get() = _movies


    fun getAllMovies() {
        viewModelScope.launch {
            getAllMoviesUseCase().collect { result ->
                when (result) {
                    is Resources.Success -> {
                        _movies.value = result.data
                    }

                    is Resources.Error -> {
                        when (result.errorCode) {
                            INVALID_LOGIN_ERROR -> {
                                _showError.value = application.getString(R.string.invalid_login)
                            }

                            NO_INTERNET_CONNECTION -> {
                                _showError.value = result.error ?: ""
                            }

                            FAILED_CONNECTION -> {
                                _showError.value = application.getString(R.string.network_error)
                            }
                        }
                    }
                }
            }
        }

    }


}