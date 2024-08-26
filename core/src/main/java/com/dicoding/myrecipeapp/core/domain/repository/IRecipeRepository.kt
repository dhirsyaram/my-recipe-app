package com.dicoding.myrecipeapp.core.domain.repository

import com.dicoding.myrecipeapp.core.data.Resource
import com.dicoding.myrecipeapp.core.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface IRecipeRepository {

    fun getListRecipes(): Flow<Resource<List<Recipe>>>

    fun getDetailRecipe(id: Int): Flow<Resource<Recipe>>

    fun getRecipeById(id: Int): Flow<Recipe>

    fun getFavoriteRecipe(): Flow<List<Recipe>>

    fun setFavoriteRecipe(recipe: Recipe, state: Boolean)
}