package com.example.finalproject.data.utils

sealed class ExceptionResource  {
    data class NetworkError(val errorCode : Int) : ExceptionResource()
    data class InvalidLogin(val errorCode: Int) : ExceptionResource()
    data class IoException(val errorCode: Int,val error: String) : ExceptionResource()
}