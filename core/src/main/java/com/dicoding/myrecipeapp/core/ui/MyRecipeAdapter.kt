package com.dicoding.myrecipeapp.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.myrecipeapp.core.R
import com.dicoding.myrecipeapp.core.databinding.ItemListRecipeBinding
import com.dicoding.myrecipeapp.core.domain.model.Recipe
import com.dicoding.myrecipeapp.core.utils.htmlParser

class MyRecipeAdapter(
    private var recipes: List<Recipe>
) : RecyclerView.Adapter<MyRecipeAdapter.RecipeViewHolder>() {

    private var onItemClickCallback: ((Recipe) -> Unit)? = null

    fun setOnItemClickCallback(callback: (Recipe) -> Unit) {
        this.onItemClickCallback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemListRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe)
        holder.itemView.setOnClickListener {
            onItemClickCallback?.invoke(recipe)
        }
    }

    inner class RecipeViewHolder(private val binding: ItemListRecipeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(recipe.image)
                    .into(ivRecipe)
                tvTitle.text = recipe.title
                tvDescription.text = htmlParser(recipe.summary)
                ivHealth.setImageResource(
                    if (recipe.veryHealthy) R.drawable.ic_health else R.drawable.ic_no_health
                )
                ivVegetarian.setImageResource(
                    if (recipe.vegetarian) R.drawable.ic_leaf else R.drawable.ic_leaf_bad
                )
                ivCheap.setImageResource(
                    if (recipe.cheap) R.drawable.ic_price else R.drawable.ic_expensive
                )
                tvTimeMinute.text = recipe.readyInMinutes.toString()
            }
        }
    }
}