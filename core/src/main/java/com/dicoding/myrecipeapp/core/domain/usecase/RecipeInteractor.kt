package com.dicoding.myrecipeapp.core.domain.usecase

import com.dicoding.myrecipeapp.core.data.Resource
import com.dicoding.myrecipeapp.core.domain.model.Recipe
import com.dicoding.myrecipeapp.core.domain.repository.IRecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipeInteractor @Inject constructor(
    private val recipeRepository: IRecipeRepository
) : RecipeUseCase {

    override fun getListRecipes(): Flow<Resource<List<Recipe>>> =
        recipeRepository.getListRecipes()

    override fun getDetailRecipe(id: Int): Flow<Resource<Recipe>> =
        recipeRepository.getDetailRecipe(id)

    override fun getRecipeById(id: Int): Flow<Recipe> =
        recipeRepository.getRecipeById(id)

    override fun getFavoriteRecipe(): Flow<List<Recipe>> =
        recipeRepository.getFavoriteRecipe()

    override fun setFavoriteRecipe(recipe: Recipe, state: Boolean) {
        recipeRepository.setFavoriteRecipe(recipe, state)
    }
}
