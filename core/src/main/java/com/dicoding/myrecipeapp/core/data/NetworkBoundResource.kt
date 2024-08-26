package com.dicoding.myrecipeapp.core.data

import com.dicoding.myrecipeapp.core.data.source.remote.network.ApiResponseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

abstract class NetworkBoundResource<ResultType, RequestType> {

    fun asFlow(): Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading(null))
        val dbSource = loadFromDB().firstOrNull()

        if (shouldFetch(dbSource)) {
            when (val apiResponse = createCall().first()) {
                is ApiResponseResult.Success -> {
                    saveCallResult(apiResponse.data)
                    emitAll(loadFromDB().map { Resource.Success(it) })
                }
                is ApiResponseResult.Empty -> {
                    emitAll(loadFromDB().map { Resource.Success(it) })
                }
                is ApiResponseResult.Error -> {
                    emit(Resource.Error(apiResponse.errorMsg, loadFromDB().firstOrNull()))
                }
            }
        } else {
            emitAll(loadFromDB().map { Resource.Success(it) })
        }
    }

    protected abstract fun loadFromDB(): Flow<ResultType>
    protected abstract fun shouldFetch(data: ResultType?): Boolean
    protected abstract suspend fun createCall(): Flow<ApiResponseResult<RequestType>>
    protected abstract suspend fun saveCallResult(data: RequestType)
}
