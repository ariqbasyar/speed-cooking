package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.viewmodels

import android.net.Uri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
    private val durationRepository: DurationRepository
): ViewModel() {

    private var recipeId : Long = 0
    private var imageUri = MutableLiveData<Uri>()
    fun recipe(): LiveData<Recipe> =
        recipeRepository.getRecipe(recipeId)

    fun ingredients(): LiveData<List<Ingredient>> =
        ingredientRepository.getIngredientsFromRecipe(recipeId)

    fun durations(): LiveData<List<Duration>> =
        durationRepository.getDurationsFromRecipe(recipeId)

    fun applyRecipeId(recipeId: Long) {
        this.recipeId = recipeId
    }

    fun setImageUri(imageUri: Uri) {
        this.imageUri.postValue(imageUri)
    }
}
