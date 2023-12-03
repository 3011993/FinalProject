package com.example.finalproject.ui

import com.example.finalproject.domain.models.MovieModel

sealed class UiModel {
    data class MoviesModel(val data : List<MovieModel>) : UiModel()
    data object Loading : UiModel()
}