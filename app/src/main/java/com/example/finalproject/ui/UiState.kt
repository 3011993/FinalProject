package com.example.finalproject.ui

import com.example.finalproject.domain.models.MoviesModel

sealed class UiState {
    data class UiModel (val data : List<MoviesModel>) : UiState()
    data class InvalidModel(val error : String) : UiState()
}