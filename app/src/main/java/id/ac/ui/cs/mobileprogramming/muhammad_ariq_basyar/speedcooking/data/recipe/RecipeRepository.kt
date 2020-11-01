package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.recipe

import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.ingredient.IngredientDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepository @Inject constructor(
    private val recipeDao: RecipeDao
) {

    suspend fun createRecipe(
        name: String,
        procedure: String,
        imageUrl: String
    ): Long {
        val recipe = Recipe(name, procedure, imageUrl)
        return recipeDao.insert(recipe)
    }

    fun getRecipes() = recipeDao.getRecipes()
    fun getRecipe(recipeId: Long) = recipeDao.getRecipe(recipeId)
}