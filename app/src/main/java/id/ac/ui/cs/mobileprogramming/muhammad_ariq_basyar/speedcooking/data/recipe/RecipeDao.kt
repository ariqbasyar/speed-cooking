package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.recipe

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes ORDER BY name")
    fun getRecipes(): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    fun getRecipe(recipeId: Long): LiveData<Recipe>

    @Insert
    suspend fun insert(recipe: Recipe): Long
}
