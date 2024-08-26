package com.dicoding.myrecipeapp.core.data

import com.dicoding.myrecipeapp.core.data.source.remote.network.ApiResponseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

abstract class NetworkOnlyResource<ResultType, RequestType> {

    private val result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = createCall().first()) {
            is ApiResponseResult.Success -> {
                emitAll(loadFromNetwork(apiResponse.data).map {
                    Resource.Success(it)
                })
            }

            is ApiResponseResult.Error -> {
                emit(Resource.Error(apiResponse.errorMsg))
            }
            else -> Unit
        }
    }

    protected abstract fun loadFromNetwork(data: RequestType): Flow<ResultType>

    protected abstract suspend fun createCall(): Flow<ApiResponseResult<RequestType>>

    fun asFlow(): Flow<Resource<ResultType>> = result

}