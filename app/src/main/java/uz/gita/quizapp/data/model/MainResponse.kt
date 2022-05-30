package uz.gita.quizapp.data.model

sealed class MainResponse<T> {
    data class Success<T>(val data: T) : MainResponse<T>()
    data class Fail<T>(val message: String) : MainResponse<T>()
    data class Error<T>(val error: Throwable) : MainResponse<T>()
}