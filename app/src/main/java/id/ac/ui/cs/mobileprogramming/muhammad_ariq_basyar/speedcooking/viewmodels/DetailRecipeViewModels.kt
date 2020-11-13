package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.duration.Duration
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.duration.DurationRepository
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.ingredient.Ingredient
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.ingredient.IngredientRepository
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.recipe.Recipe
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.recipe.RecipeRepository
import kotlinx.coroutines.runBlocking

class DetailRecipeViewModels @ViewModelInject internal constructor(
    private val recipeRepository: RecipeRepository,
    private val ingredientRepository: IngredientRepository,
    private val durationRepository: DurationRepository,
    application: Application
): AndroidViewModel(application) {

    private lateinit var ingredients : LiveData<List<Ingredient>>
    private lateinit var durations : LiveData<List<Duration>>
    private lateinit var recipe: LiveData<Recipe>

    fun applyRecipeId(recipeId: Long) {
        fetchRecipe(recipeId)
        fetchIngredients(recipeId)
        fetchDurations(recipeId)
    }

    private fun fetchRecipe(recipeId: Long) {
        recipe = recipeRepository.getRecipe(recipeId)
    }

    private fun fetchIngredients(recipeId: Long) {
        ingredients = ingredientRepository.getIngredientsFromRecipe(recipeId)
    }

    private fun fetchDurations(recipeId: Long) {
        durations = durationRepository.getDurationsFromRecipe(recipeId)
    }

    fun getRecipe(): LiveData<Recipe> {
        return recipe
    }

    fun getIngredients(): LiveData<List<Ingredient>> {
        return ingredients
    }

    fun getDurations(): LiveData<List<Duration>> {
        return durations
    }
}
