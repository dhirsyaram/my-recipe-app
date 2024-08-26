package com.dicoding.myrecipeapp.favorite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.myrecipeapp.core.domain.usecase.RecipeUseCase
import javax.inject.Inject

class FavoriteVMFactory @Inject constructor(
    private val recipeUseCase: RecipeUseCase
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            FavoriteViewModel::class.java -> FavoriteViewModel(recipeUseCase) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
