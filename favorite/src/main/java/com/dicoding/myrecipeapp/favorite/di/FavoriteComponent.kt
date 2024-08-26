package com.dicoding.myrecipeapp.favorite.di

import android.content.Context
import com.dicoding.myrecipeapp.di.FavoriteModuleDependencies
import com.dicoding.myrecipeapp.favorite.FavoriteFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [FavoriteModuleDependencies::class]
)
interface FavoriteComponent {

    fun inject(favoriteFragment: FavoriteFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder

        fun favoriteModuleDependencies(dependencies: FavoriteModuleDependencies): Builder

        fun build(): FavoriteComponent
    }
}