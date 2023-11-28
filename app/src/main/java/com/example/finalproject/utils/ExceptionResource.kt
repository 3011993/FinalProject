package com.example.finalproject.utils

sealed class ExceptionResource  {
    data class NetworkError(val error : String) : ExceptionResource()
    data class InvalidLogin(val error: String) : ExceptionResource()
    data class IoException(val error: String) : ExceptionResource()
}