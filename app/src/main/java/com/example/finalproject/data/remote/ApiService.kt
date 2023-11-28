package com.example.finalproject.data.remote

import retrofit2.Response

interface ApiService {

    suspend fun getAllMovies() : Response<List<MovieDto>>
}