package com.dicoding.myrecipeapp.di

import com.dicoding.myrecipeapp.core.domain.usecase.RecipeUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {
    fun recipeUseCase(): RecipeUseCase
}