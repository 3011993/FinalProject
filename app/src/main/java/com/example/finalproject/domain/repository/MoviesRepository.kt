package com.example.finalproject.domain.repository

import com.example.finalproject.domain.models.MoviesModel
import com.example.finalproject.utils.Resources
import kotlinx.coroutines.flow.Flow


interface MoviesRepository {
    suspend fun getAllMovies() : Flow<Resources<List<MoviesModel>>>
}