package com.travoro.app.core.network

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()

    data class Error(val code: Int, val message: String) : ApiResult<Nothing>()

    data class Exception(val message: String) : ApiResult<Nothing>()
}
