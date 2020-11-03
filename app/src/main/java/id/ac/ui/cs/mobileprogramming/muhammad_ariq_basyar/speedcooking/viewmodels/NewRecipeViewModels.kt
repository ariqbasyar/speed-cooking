package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.viewmodels

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.recipe.RecipeRepository

class NewRecipeViewModels @ViewModelInject internal constructor (
    recipeRepository: RecipeRepository
): ViewModel() {

    var name: String = ""
    var procedure: String = ""
    var image_url: String = ""

    fun save() {
        Log.d("ASDASD", "submit name: $name")
        Log.d("ASDASD", "submit procedure: $procedure")
        Log.d("ASDASD", "submit image url: $image_url")
    }
}