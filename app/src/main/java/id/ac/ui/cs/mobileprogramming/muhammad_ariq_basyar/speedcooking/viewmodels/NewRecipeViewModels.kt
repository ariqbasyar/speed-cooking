package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.viewmodels

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.ingredient.IngredientRepository
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.recipe.RecipeRepository

class NewRecipeViewModels @ViewModelInject internal constructor(
    recipeRepository: RecipeRepository,
    ingredientRepository: IngredientRepository,
    application: Application
): AndroidViewModel(application) {

    var name = ""
    var procedure = ""
    private var ingredients = ArrayList<String>()
    var imageUrl = MutableLiveData<Uri>()

    fun setImageUrl(imageUrl: Uri?) {
        this.imageUrl.postValue(imageUrl)
    }

    fun addIngredient(ingredient: String) {
        ingredients.add(ingredient)
    }

    fun save() {
        Log.d("ASDASD", "save name: $name")
        Log.d("ASDASD", "save procedure: $procedure")
        Log.d("ASDASD", "save ingredient: ${ingredients.joinToString()}")
        Log.d("ASDASD", "save image url: ${imageUrl.value.toString()}")
    }
}