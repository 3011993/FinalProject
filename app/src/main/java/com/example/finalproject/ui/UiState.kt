package com.example.finalproject.ui

const val DEFAULT_ERROR = "UnKnown error occurred!"
sealed class UiState(){
    data object InvalidLogin : UiState()
    data object NetworkError : UiState()
    class IoException(val error : String): UiState()
}


