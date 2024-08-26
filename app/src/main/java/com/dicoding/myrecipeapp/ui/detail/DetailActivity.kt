package com.dicoding.myrecipeapp.ui.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.dicoding.myrecipeapp.R
import com.dicoding.myrecipeapp.core.data.Resource
import com.dicoding.myrecipeapp.core.domain.model.Recipe
import com.dicoding.myrecipeapp.core.utils.htmlParser
import com.dicoding.myrecipeapp.core.utils.setImageViewTint
import com.dicoding.myrecipeapp.core.utils.setTextViewColor
import com.dicoding.myrecipeapp.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var detailBind: ActivityDetailBinding
    private val args: DetailActivityArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBind = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBind.root)

        supportActionBar?.hide()

        observeRecipeDetails(args.id)
        detailBind.fabBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    private fun observeRecipeDetails(recipeId: Int) {
        viewModel.getDetailRecipe(recipeId).observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> true.showLoading()
                is Resource.Success -> resource.data?.let { data -> setData(data) }
                is Resource.Error -> showError(resource.message)
                else -> Unit
            }
        }
    }

    private fun setData(data: Recipe) {
        with(detailBind) {
            progressBarDetail.visibility = View.GONE
            frameLayout.visibility = View.VISIBLE
            containerQuality.visibility = View.VISIBLE

            Glide.with(this@DetailActivity)
                .load(data.image)
                .into(ivDetail)

            tvDetailTitle.text = data.title
            updateUIBasedOnCondition(data)
            tvDetailDescription.text = htmlParser(data.summary)

            viewModel.getRecipeById(data.recipeId).observe(this@DetailActivity) { recipe ->
                isFavorite = recipe?.isFavorite ?: false
                updateFavoriteButton(isFavorite)
            }
            fabFavoriteClicked(data)
        }
    }

    private fun updateUIBasedOnCondition(data: Recipe) {
        with(detailBind) {
            setImageViewTint(applicationContext, ivDetailHealthy, data.veryHealthy)
            setTextViewColor(applicationContext, tvDetailHealthy, data.veryHealthy)
            setImageViewTint(applicationContext, ivDetailVegetarian, data.vegetarian)
            setTextViewColor(applicationContext, tvDetailVegetarian, data.vegetarian)
            setImageViewTint(applicationContext, ivDetailVegan, data.vegan)
            setTextViewColor(applicationContext, tvDetailVegan, data.vegan)
            setImageViewTint(applicationContext, ivDetailCheap, data.cheap)
            setTextViewColor(applicationContext, tvDetailCheap, data.cheap)
            setImageViewTint(applicationContext, ivDetailDairyFree, data.dairyFree)
            setTextViewColor(applicationContext, tvDetailDairyFree, data.dairyFree)
            setImageViewTint(applicationContext, ivDetailGlutenFree, data.glutenFree)
            setTextViewColor(applicationContext, tvDetailGlutenFree, data.glutenFree)
        }
    }

    private fun fabFavoriteClicked(data: Recipe) {
        detailBind.fabFavorite.setOnClickListener {
            isFavorite = !isFavorite
            viewModel.setFavoriteRecipe(data, isFavorite)
            updateFavoriteButton(isFavorite)
        }
    }

    private fun updateFavoriteButton(isFavorite: Boolean) {
        detailBind.fabFavorite.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                if (isFavorite) R.drawable.ic_heart else R.drawable.ic_heart_outline
            )
        )
    }

    private fun Boolean.showLoading() {
        with(detailBind) {
            progressBarDetail.visibility = if (this@showLoading) View.VISIBLE else View.GONE
            frameLayout.visibility = if (this@showLoading) View.GONE else View.VISIBLE
            containerQuality.visibility = if (this@showLoading) View.GONE else View.VISIBLE
        }
    }

    private fun showError(message: String?) {
        with(detailBind) {
            viewError.root.visibility = View.VISIBLE
            viewError.tvError.text = message ?: getString(R.string.something_wrong)
            progressBarDetail.visibility = View.GONE
            clDetail.visibility = View.GONE
            frameLayout.visibility = View.GONE
        }
    }
}










