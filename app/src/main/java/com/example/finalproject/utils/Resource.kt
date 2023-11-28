package com.example.finalproject.utils

import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception


const val NO_INTERNET_CONNECTION = 404
const val FAILED_CONNECTION = 22
const val INVALID_LOGIN_ERROR = 112

sealed class Resources<T>(val data: T? = null, val exception: ExceptionResource? = null) {
    class Success<T>(data: T?) : Resources<T>(data)
    class Error<T>(exception: ExceptionResource) :
        Resources<T>(exception = exception)

}

fun <T> handleError(exception: ExceptionResource): Resources<T> {
    return when (exception) {
        is ExceptionResource.IoException -> {
            Resources.Error<T>(
                exception = ExceptionResource.IoException(
                    errorCode = NO_INTERNET_CONNECTION,
                    error = ""
                )
            )
        }
        is ExceptionResource.NetworkError -> {
            Resources.Error<T>(exception = ExceptionResource.NetworkError(errorCode = FAILED_CONNECTION))
        }
        else -> {
            Resources.Error<T>(exception = ExceptionResource.InvalidLogin(errorCode = INVALID_LOGIN_ERROR))
        }
    }
}