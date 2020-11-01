package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.ingredient

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IngredientRepository @Inject constructor(
    private val ingredientDao: IngredientDao
) {

    suspend fun createIngredient(recipeId: Long, recipeIngredient: String): Long {
        val ingredient = Ingredient(recipeId, recipeIngredient)
        return ingredientDao.insertIngredient(ingredient)
    }

    suspend fun createIngredients(recipeId: Long, recipeIngredients: List<String>) {
        val ingredients: List<Ingredient> = recipeIngredients.map {
            recipeIngredient: String -> Ingredient(recipeId, recipeIngredient)
        }
        ingredientDao.insertAll(ingredients)
    }

    fun getIngredientsFromRecipe(recipeId: Long) =
        ingredientDao.getIngredientsFromRecipe(recipeId)
}
