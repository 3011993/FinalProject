package com.example.finalproject.data.remote

import com.example.finalproject.domain.models.MoviesModel

data class MovieDto(
    val movieId: String,
    val movie: String
)

fun MovieDto.toMovieModel(): MoviesModel {
    return MoviesModel(
        movieId,
        movie
    )
}