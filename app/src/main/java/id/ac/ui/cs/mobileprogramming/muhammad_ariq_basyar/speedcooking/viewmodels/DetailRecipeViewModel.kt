package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.duration.Duration
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.duration.DurationRepository
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.ingredient.Ingredient
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.ingredient.IngredientRepository
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.recipe.Recipe
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.recipe.RecipeRepository
import kotlinx.coroutines.launch

class DetailRecipeViewModel @ViewModelInject internal constructor(
    private val recipeRepository: RecipeRepository,
    private val ingredientRepository: IngredientRepository,
    private val durationRepository: DurationRepository
): ViewModel() {

    private var recipeId : Long = 0
    private lateinit var recipeName: String

    fun setRecipeName(name: String) {
        recipeName = name
    }

    fun getRecipeName(): String = recipeName

    fun recipe(): LiveData<Recipe> =
        recipeRepository.getRecipe(recipeId)

    fun ingredients(): LiveData<List<Ingredient>> =
        ingredientRepository.getIngredientsFromRecipe(recipeId)

    fun durations(): LiveData<List<Duration>> =
        durationRepository.getDurationsFromRecipe(recipeId)

    fun applyRecipeId(recipeId: Long) {
        this.recipeId = recipeId
    }

    fun saveDuration(elapsedTime: Long) {
        viewModelScope.launch {
            durationRepository.createDuration(recipeId, elapsedTime)
            durationRepository.deleteDurationsExceptTheTopThree(recipeId)
        }
    }
}
