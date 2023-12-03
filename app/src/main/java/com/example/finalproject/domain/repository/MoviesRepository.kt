package com.example.finalproject.domain.repository

import com.example.finalproject.domain.models.MovieModel
import com.example.finalproject.data.utils.Resources
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun getAllMovies() : Flow<Resources<List<MovieModel>>>
}