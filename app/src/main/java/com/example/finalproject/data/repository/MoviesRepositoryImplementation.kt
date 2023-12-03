package com.example.finalproject.data.repository

import com.example.finalproject.data.remote.ApiService
import com.example.finalproject.data.remote.toMovieModel
import com.example.finalproject.domain.models.MovieModel
import com.example.finalproject.domain.repository.MoviesRepository
import com.example.finalproject.data.utils.ExceptionResource
import com.example.finalproject.data.utils.FAILED_CONNECTION
import com.example.finalproject.data.utils.INVALID_LOGIN_ERROR
import com.example.finalproject.data.utils.Resources
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException


class MoviesRepositoryImplementation(private val api: ApiService) : MoviesRepository {

    override suspend fun getAllMovies(): Flow<Resources<List<MovieModel>>> {
        return flow {
            try {
                emit(Resources.Loading<List<MovieModel>>())
                val response = api.getAllMovies()
                val moviesList = response.body()
                emit(Resources.Success<List<MovieModel>>(moviesList?.map { it.toMovieModel() }))
            } catch (exception: HttpException) {
                emit(
                    Resources.Error<List<MovieModel>>(
                        exception = ExceptionResource.NetworkError(
                            exception.code()
                        )
                    )
                )
            } catch (exception: IOException) {
                emit(
                    Resources.Error<List<MovieModel>>(
                        exception = ExceptionResource.IoException(
                            FAILED_CONNECTION,
                            exception.message ?: ""
                        )
                    )
                )
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: Exception) {

                emit(
                    Resources.Error<List<MovieModel>>(
                        exception = ExceptionResource.InvalidLogin(
                            INVALID_LOGIN_ERROR
                        )
                    )
                )
            }
        }
    }
}