package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.ingredient

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface IngredientDao {
    @Transaction
    @Query("SELECT * FROM ingredients where recipe_id = :recipeId ORDER BY id ASC")
    fun getIngredientsFromRecipe(recipeId: Long): LiveData<List<Ingredient>>

    @Insert
    suspend fun insertIngredient(ingredient: Ingredient): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(durations: List<Ingredient>)
}
