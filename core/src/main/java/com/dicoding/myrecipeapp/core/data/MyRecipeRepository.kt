package com.dicoding.myrecipeapp.core.data

import com.dicoding.myrecipeapp.core.data.source.local.LocalDataSource
import com.dicoding.myrecipeapp.core.data.source.remote.RemoteDataSource
import com.dicoding.myrecipeapp.core.data.source.remote.network.ApiResponseResult
import com.dicoding.myrecipeapp.core.data.source.remote.response.RecipeResponse
import com.dicoding.myrecipeapp.core.domain.model.Recipe
import com.dicoding.myrecipeapp.core.domain.repository.IRecipeRepository
import com.dicoding.myrecipeapp.core.utils.AppExecutors
import com.dicoding.myrecipeapp.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyRecipeRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): IRecipeRepository {

    override fun getListRecipes(): Flow<Resource<List<Recipe>>> =
        object : NetworkBoundResource<List<Recipe>, List<RecipeResponse>>() {

            override fun loadFromDB(): Flow<List<Recipe>> {
                return localDataSource.getListRecipes().map { entities ->
                    DataMapper.mapEntitiesToDomain(entities)
                }
            }

            override suspend fun createCall(): Flow<ApiResponseResult<List<RecipeResponse>>> =
                remoteDataSource.getRecipes()

            override suspend fun saveCallResult(data: List<RecipeResponse>) {
                val recipeList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertRecipe(recipeList)
            }

            override fun shouldFetch(data: List<Recipe>?): Boolean =
                data.isNullOrEmpty()

        }.asFlow()

    override fun getDetailRecipe(id: Int): Flow<Resource<Recipe>> {
        return object : NetworkOnlyResource<Recipe, RecipeResponse>() {
            override fun loadFromNetwork(data: RecipeResponse): Flow<Recipe> {
                return DataMapper.mapResponseToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponseResult<RecipeResponse>> {
                return remoteDataSource.getDetailRecipe(id)
            }

        }.asFlow()
    }

    override fun getRecipeById(id: Int): Flow<Recipe> {
        return localDataSource.getRecipeById(id).map {
            DataMapper.mapEntityToDomain(it)
        }
    }

    override fun getFavoriteRecipe(): Flow<List<Recipe>> {
        return localDataSource.getFavoriteRecipe().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteRecipe(recipe: Recipe, state: Boolean) {
        val recipeEntity = DataMapper.mapDomainToEntity(recipe)
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteRecipe(recipeEntity, state)
        }
    }

}











