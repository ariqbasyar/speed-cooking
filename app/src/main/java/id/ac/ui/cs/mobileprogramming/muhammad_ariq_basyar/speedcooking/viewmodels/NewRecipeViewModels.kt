package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.viewmodels

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.ingredient.IngredientRepository
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.recipe.RecipeRepository
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*
import kotlin.collections.ArrayList

class NewRecipeViewModels @ViewModelInject internal constructor(
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

    private fun getBitmapFromUri(imageUri: Uri): Bitmap? {
        var bitmap: Bitmap? = null

        try {
            bitmap = MediaStore.Images.Media.getBitmap(
                contentResolver,
                imageUri
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return bitmap
    }

    private fun saveToInternalStorage(imageUri: Uri): File? {
        val bitmap: Bitmap? = getBitmapFromUri(imageUri)
        var file: File? = null
        bitmap?.let { mBitmap ->
            file = context.getDir("images", Context.MODE_PRIVATE)
            file = File(file, "${UUID.randomUUID()}.jpg")
            try {
                val stream: OutputStream = FileOutputStream(file)

                mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                stream.flush()
                stream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return file
    }

    fun save() {
        viewModelScope.launch {
            val imageFileDescriptor: File? = imageUri.value?.let { saveToInternalStorage(it) }
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