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

    @Insert
    suspend fun insertDuration(duration: Duration): Long
}
