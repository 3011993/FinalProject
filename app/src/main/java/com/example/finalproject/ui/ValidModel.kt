package com.example.finalproject.ui

import com.example.finalproject.domain.models.MoviesModel

sealed class UiModel {
    data class ValidModel (val data : List<MoviesModel>) : UiModel()
    data class InvalidModel(val state: UiState) : UiModel()
}