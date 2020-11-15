package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.duration

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DurationDao {
    @Transaction
    @Query("""
        SELECT *
        FROM durations
        WHERE recipe_id = :recipeId
        ORDER BY recipe_duration ASC
        LIMIT 3
    """)
    fun getDurationsFromRecipe(recipeId: Long): LiveData<List<Duration>>

    @Transaction
    @Query("""
        DELETE FROM durations
        WHERE id NOT IN (
            SELECT id
            FROM durations
            WHERE recipe_id = :recipeId
            ORDER BY recipe_duration ASC
            LIMIT 3
        ) AND recipe_id = :recipeId
    """)
    suspend fun deleteDurationsExceptTheTopThree(recipeId: Long)

    @Insert
    suspend fun insertDuration(duration: Duration): Long
}
