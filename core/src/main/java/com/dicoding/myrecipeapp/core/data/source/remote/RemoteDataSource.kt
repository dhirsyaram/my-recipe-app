package com.dicoding.myrecipeapp.core.data.source.remote

import android.util.Log
import com.dicoding.myrecipeapp.core.data.source.remote.network.ApiResponseResult
import com.dicoding.myrecipeapp.core.data.source.remote.network.ApiService
import com.dicoding.myrecipeapp.core.data.source.remote.response.RecipeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    fun getRecipes(): Flow<ApiResponseResult<List<RecipeResponse>>> = flow {
        try {
            val response = apiService.getListRecipes()
            val recipes = response.results

            if (recipes.isNotEmpty()) {
                emit(ApiResponseResult.Success(recipes))
            } else {
                emit(ApiResponseResult.Empty)
            }
        } catch (e: Exception) {
            emit(ApiResponseResult.Error(e.toString()))
            Log.e("RemoteDataSource", e.toString())
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getDetailRecipe(id: Int): Flow<ApiResponseResult<RecipeResponse>> {
        return flow {
            try {
                val response = apiService.getDetailRecipe(id)
                emit(ApiResponseResult.Success(response))
            } catch (e: Exception) {
                emit(ApiResponseResult.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}