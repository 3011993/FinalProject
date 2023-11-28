package com.example.finalproject.domain.use_cases

import com.example.finalproject.domain.repository.MoviesRepository

class GetAllMoviesUseCase(private val repo : MoviesRepository) {

    suspend operator fun invoke() = repo.getAllMovies()
}