package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.viewmodels

import android.app.Application
import android.net.Uri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.ingredient.IngredientRepository
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.recipe.RecipeRepository
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.utils.ImageUtils.Companion.saveFromExternalToInternalStorage
import kotlinx.coroutines.launch
import java.io.File
import kotlin.collections.ArrayList

class NewRecipeViewModel @ViewModelInject internal constructor(
    private val recipeRepository: RecipeRepository,
    private val ingredientRepository: IngredientRepository,
    application: Application
): AndroidViewModel(application) {

    var name = ""
    var procedure = ""
    private var ingredients = ArrayList<String>()
    private val contentResolver = getApplication<Application>().contentResolver
    private val context = getApplication<Application>().applicationContext
    var imageUri = MutableLiveData<Uri>()

    fun setImageUri(imageUrl: Uri?) {
        this.imageUri.postValue(imageUrl)
    }

    fun addIngredient(ingredient: String) {
        ingredients.add(ingredient)
    }

    fun save() {
        viewModelScope.launch {
            val filePath = File(context.filesDir, "images")
            filePath.mkdirs()
            val imageFileDescriptor: File? = imageUri.value?.let {
                saveFromExternalToInternalStorage(it, filePath, contentResolver)
            }
            val imageAbsUri: Uri? = imageFileDescriptor?.let { file -> Uri.parse(file.absolutePath) }
            val newRecipeId: Long? = imageAbsUri?.let { imageUri ->
                recipeRepository.createRecipe(name, procedure, imageUri)
            }
            if (newRecipeId != null) {
                ingredientRepository.createIngredients(newRecipeId, ingredients)
            }
        }
    }
}