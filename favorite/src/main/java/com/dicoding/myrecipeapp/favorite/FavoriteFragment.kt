package com.dicoding.myrecipeapp.favorite

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.myrecipeapp.core.domain.model.Recipe
import com.dicoding.myrecipeapp.core.ui.MyRecipeAdapter
import com.dicoding.myrecipeapp.di.FavoriteModuleDependencies
import com.dicoding.myrecipeapp.favorite.databinding.FragmentFavoriteBinding
import com.dicoding.myrecipeapp.favorite.di.DaggerFavoriteComponent
import com.dicoding.myrecipeapp.favorite.viewmodel.FavoriteVMFactory
import com.dicoding.myrecipeapp.favorite.viewmodel.FavoriteViewModel
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: FavoriteVMFactory
    private val favoriteViewModel: FavoriteViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        DaggerFavoriteComponent.builder()
            .context(requireActivity())
            .favoriteModuleDependencies(
                EntryPointAccessors.fromApplication(
                    requireActivity().applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeFavoriteRecipes()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        binding.rvRecipe.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
        }
    }

    private fun observeFavoriteRecipes() {
        favoriteViewModel.getFavoriteRecipe().observe(viewLifecycleOwner) { recipes ->
            if (recipes.isNullOrEmpty()) {
                showNoDataView()
            } else {
                showRecipeList(recipes)
            }
        }
    }

    private fun showNoDataView() {
        binding.rvRecipe.visibility = View.GONE
        binding.tvNoData.visibility = View.VISIBLE
    }

    private fun showRecipeList(recipes: List<Recipe>) {
        binding.rvRecipe.visibility = View.VISIBLE
        binding.tvNoData.visibility = View.GONE
        setupAdapter(recipes)
    }

    private fun setupAdapter(recipes: List<Recipe>) {
        val adapter = MyRecipeAdapter(recipes).apply {
            setOnItemClickCallback { recipe ->
                navigateToDetail(recipe)
            }
        }
        binding.rvRecipe.adapter = adapter
    }

    private fun navigateToDetail(recipe: Recipe) {
        val action = FavoriteFragmentDirections.actionNavigationFavoriteToDetailActivity(
            recipe.recipeId
        )
        findNavController().navigate(action)
    }
}