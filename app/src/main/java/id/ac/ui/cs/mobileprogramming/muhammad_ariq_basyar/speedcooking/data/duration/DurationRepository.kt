package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.duration

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DurationRepository @Inject constructor(
    private val durationDao: DurationDao
) {

    suspend fun createDuration(recipeId: Long, recipeDuration: Long): Long {
        val duration = Duration(recipeId, recipeDuration)
        return durationDao.insertDuration(duration)
    }

    suspend fun deleteDurationsExceptTheTopThree(recipeId: Long) =
        durationDao.deleteDurationsExceptTheTopThree(recipeId)

    fun getDurationsFromRecipe(recipeId: Long) =
        durationDao.getDurationsFromRecipe(recipeId)
}
