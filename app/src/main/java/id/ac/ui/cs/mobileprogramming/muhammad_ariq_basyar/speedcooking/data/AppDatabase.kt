package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data

import android.content.Context
import androidx.room.*
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.converter.Converters
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.duration.Duration
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.duration.DurationDao
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.ingredient.Ingredient
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.ingredient.IngredientDao
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.recipe.Recipe
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.recipe.RecipeDao

@Database(entities = [Recipe::class, Ingredient::class, Duration::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun ingredientDao(): IngredientDao
    abstract fun durationDao(): DurationDao

    companion object {
        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null
        private const val DATABASE_NAME = "speedcooking-db"

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}
