package com.example.finalproject.data.remote

import com.example.finalproject.domain.models.MovieModel

data class MovieDto(
    val movieId: String,
    val movie: String
)

fun MovieDto.toMovieModel(): MovieModel {
    return MovieModel(
        movieId,
        movie
    )
}