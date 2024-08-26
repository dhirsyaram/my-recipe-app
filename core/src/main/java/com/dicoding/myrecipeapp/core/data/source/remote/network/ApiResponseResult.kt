package com.dicoding.myrecipeapp.core.data.source.remote.network

sealed class ApiResponseResult<out R> {
    data class Success<out T>(val data: T) : ApiResponseResult<T>()
    data class Error(val errorMsg: String) : ApiResponseResult<Nothing>()
    data object Empty : ApiResponseResult<Nothing>()
}