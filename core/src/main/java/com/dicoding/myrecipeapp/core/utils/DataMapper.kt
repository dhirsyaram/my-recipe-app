package com.dicoding.myrecipeapp.core.utils

import com.dicoding.myrecipeapp.core.data.source.local.entity.RecipeEntity
import com.dicoding.myrecipeapp.core.data.source.remote.response.RecipeResponse
import com.dicoding.myrecipeapp.core.domain.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object DataMapper {

    fun mapResponsesToEntities(input: List<RecipeResponse>): List<RecipeEntity> =
        input.map { response ->
            RecipeEntity(
                recipeId = response.id,
                title = response.title,
                image = response.image,
                healthScore = response.healthScore,
                dairyFree = response.dairyFree,
                glutenFree = response.glutenFree,
                aggregateLikes = response.aggregateLikes,
                readyInMinutes = response.readyInMinutes,
                vegetarian = response.vegetarian,
                summary = response.summary,
                veryHealthy = response.veryHealthy,
                vegan = response.vegan,
                cheap = response.cheap,
                isFavorite = false
            )
        }

    fun mapResponseToDomain(input: RecipeResponse): Flow<Recipe> =
        flowOf(
            Recipe(
                title = input.title,
                image = input.image,
                recipeId = input.id,
                summary = input.summary,
                aggregateLikes = input.aggregateLikes,
                isFavorite = false,
                readyInMinutes = input.readyInMinutes,
                healthScore = input.healthScore,
                veryHealthy = input.veryHealthy,
                vegetarian = input.vegetarian,
                vegan = input.vegan,
                cheap = input.cheap,
                dairyFree = input.dairyFree,
                glutenFree = input.glutenFree
            )
        )

    fun mapEntitiesToDomain(input: List<RecipeEntity>): List<Recipe> =
        input.map { entity ->
            Recipe(
                title = entity.title,
                image = entity.image,
                recipeId = entity.recipeId,
                summary = entity.summary,
                aggregateLikes = entity.aggregateLikes,
                isFavorite = entity.isFavorite,
                readyInMinutes = entity.readyInMinutes,
                healthScore = entity.healthScore,
                veryHealthy = entity.veryHealthy,
                vegetarian = entity.vegetarian,
                vegan = entity.vegan,
                cheap = entity.cheap,
                dairyFree = entity.dairyFree,
                glutenFree = entity.glutenFree
            )
        }

    fun mapEntityToDomain(input: RecipeEntity): Recipe =
        Recipe(
            title = input.title,
            image = input.image,
            recipeId = input.recipeId,
            summary = input.summary,
            aggregateLikes = input.aggregateLikes,
            isFavorite = input.isFavorite,
            readyInMinutes = input.readyInMinutes,
            healthScore = input.healthScore,
            veryHealthy = input.veryHealthy,
            vegetarian = input.vegetarian,
            vegan = input.vegan,
            cheap = input.cheap,
            dairyFree = input.dairyFree,
            glutenFree = input.glutenFree
        )

    fun mapDomainToEntity(input: Recipe): RecipeEntity =
        RecipeEntity(
            title = input.title,
            image = input.image,
            recipeId = input.recipeId,
            summary = input.summary,
            aggregateLikes = input.aggregateLikes,
            isFavorite = input.isFavorite,
            readyInMinutes = input.readyInMinutes,
            healthScore = input.healthScore,
            veryHealthy = input.veryHealthy,
            vegetarian = input.vegetarian,
            vegan = input.vegan,
            cheap = input.cheap,
            dairyFree = input.dairyFree,
            glutenFree = input.glutenFree
        )
}