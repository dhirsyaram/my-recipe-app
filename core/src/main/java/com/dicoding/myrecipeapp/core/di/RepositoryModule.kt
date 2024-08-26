package com.dicoding.myrecipeapp.core.di

import com.dicoding.myrecipeapp.core.data.MyRecipeRepository
import com.dicoding.myrecipeapp.core.domain.repository.IRecipeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(recipeRepository: MyRecipeRepository): IRecipeRepository
}