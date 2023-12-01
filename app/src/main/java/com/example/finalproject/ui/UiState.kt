package com.example.finalproject.ui

sealed class UiState(){
    data object InvalidLogin : UiState()
    data object NetworkError : UiState()
    data object Loading : UiState()
    class IoException(val error : String): UiState()
}


