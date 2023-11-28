package com.example.finalproject.data.repository

import com.example.finalproject.data.remote.ApiService
import com.example.finalproject.data.remote.toMovieModel
import com.example.finalproject.domain.models.MoviesModel
import com.example.finalproject.domain.repository.MoviesRepository
import com.example.finalproject.utils.ExceptionResource
import com.example.finalproject.utils.FAILED_CONNECTION
import com.example.finalproject.utils.INVALID_LOGIN_ERROR
import com.example.finalproject.utils.Resources
import com.example.finalproject.utils.handleError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException


class MoviesRepositoryImplementation(private val api: ApiService) : MoviesRepository {

    override suspend fun getAllMovies(): Flow<Resources<List<MoviesModel>>> {
        return flow {
            try {
                val response = api.getAllMovies()
                val moviesList = response.body()
                emit(Resources.Success<List<MoviesModel>>(moviesList?.map { it.toMovieModel() }))
            } catch (exception: HttpException) {
                emit(
                    Resources.Error<List<MoviesModel>>(
                        exception = ExceptionResource.NetworkError(
                            exception.code()
                        )
                    )
                )
            } catch (exception: IOException) {
                emit(
                    Resources.Error<List<MoviesModel>>(
                        exception = ExceptionResource.IoException(
                            FAILED_CONNECTION,
                            exception.message ?: ""
                        )
                    )
                )

            } catch (exception: Exception) {
                emit(
                    Resources.Error<List<MoviesModel>>(
                        exception = ExceptionResource.InvalidLogin(
                            INVALID_LOGIN_ERROR
                        )
                    )
                )

            }
        }
    }
}