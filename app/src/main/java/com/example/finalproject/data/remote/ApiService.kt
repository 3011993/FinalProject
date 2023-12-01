package com.example.finalproject.data.remote

import retrofit2.Response
import retrofit2.http.GET

const val GET_QUERY = ""
interface ApiService {
    @GET(GET_QUERY)
    suspend fun getAllMovies() : Response<List<MovieDto>>
}