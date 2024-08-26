package com.dicoding.myrecipeapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.myrecipeapp.R
import com.dicoding.myrecipeapp.core.data.Resource
import com.dicoding.myrecipeapp.core.domain.model.Recipe
import com.dicoding.myrecipeapp.core.ui.MyRecipeAdapter
import com.dicoding.myrecipeapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeVM: HomeViewModel by viewModels()

    private var _homeBind: FragmentHomeBinding? = null
    private val homeBind get() = _homeBind!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _homeBind = FragmentHomeBinding.inflate(inflater, container, false)
        return homeBind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeRecipes()
    }

    private fun setupRecyclerView() {
        homeBind.rvRecipe.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
        }
    }

    private fun observeRecipes() {
        homeVM.recipe.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> showLoading(true)
                is Resource.Success -> resource.data?.let { data ->
                    showLoading(false)
                    setData(data)
                } ?: run {
                    showLoading(false)
                    showError(getString(R.string.something_wrong))
                }
                is Resource.Error -> {
                    showLoading(false)
                    showError(resource.message ?: getString(R.string.something_wrong))
                }
            }
        }
    }

    private fun setData(recipes: List<Recipe>) {
        val adapter = MyRecipeAdapter(recipes).apply {
            setOnItemClickCallback { recipe ->
                navigateToDetail(recipe.recipeId)
            }
        }
        homeBind.rvRecipe.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        homeBind.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showError(message: String) {
        with(homeBind.viewError) {
            root.visibility = View.VISIBLE
            tvError.text = message
        }
    }

    private fun navigateToDetail(recipeId: Int) {
        val action = HomeFragmentDirections.actionNavigationHomeToDetailActivity(recipeId)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _homeBind = null
    }
}
