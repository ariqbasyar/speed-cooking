package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.ui.recipe.NewRecipeActivity
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.R
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.adapters.RecipeAdapter
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.ingredient.IngredientRepository
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.recipe.Recipe
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.databinding.FragmentHomeBinding
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.ui.recipe.DetailRecipeActivity
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

const val RECIPE_KEY = "recipeKey"

@AndroidEntryPoint
class HomeFragment : Fragment(), RecipeAdapter.RecipeClickListener {

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }
    @Inject
    lateinit var ingredientRepository: IngredientRepository
    private lateinit var binding: FragmentHomeBinding
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )

        recipeAdapter = RecipeAdapter(this)
        binding.recycler.adapter = recipeAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab.setOnClickListener { _ ->
            val intent = Intent(context, NewRecipeActivity::class.java)
            startActivity(intent)
        }

        homeViewModel.recipeList.observe(viewLifecycleOwner, { recipes ->
            recipeAdapter.recipeList = recipes
        })
    }

    override fun onRecipeClicked(recipe: Recipe) {
        val recipeId = recipe.recipeId
        val detailRecipe = Intent(context, DetailRecipeActivity::class.java)
        detailRecipe.putExtra(RECIPE_KEY, recipeId)
        startActivity(detailRecipe)
    }
}