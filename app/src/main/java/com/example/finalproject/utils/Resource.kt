package com.example.finalproject.utils

import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception


const val NO_INTERNET_CONNECTION = 404
const val FAILED_CONNECTION = 22
const val INVALID_LOGIN_ERROR = 112

sealed class Resources<T>(val data: T? = null, val errorCode: Int? = 0, val error: String? = "") {
    class Success<T>(data: T?) : Resources<T>(data)
    class Error<T>(errorCode: Int? = 0, error: String? = "") :
        Resources<T>(errorCode = errorCode, error = error)

}

fun <T> handleError(exception: Exception): Resources<T> {
    return if (exception is IOException) {
        Resources.Error<T>(errorCode = NO_INTERNET_CONNECTION, error = null)
    } else if (exception is HttpException) {
        Resources.Error<T>(errorCode = FAILED_CONNECTION, error = null)
    } else {
        Resources.Error<T>(errorCode = INVALID_LOGIN_ERROR, error = null)
    }
}