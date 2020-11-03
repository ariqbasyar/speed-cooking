package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.AppDatabase
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.duration.DurationDao
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.ingredient.IngredientDao
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.recipe.RecipeDao
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideRecipeDao(appDatabase: AppDatabase): RecipeDao {
        return appDatabase.recipeDao()
    }

    @Provides
    fun provideIngredientDao(appDatabase: AppDatabase): IngredientDao {
        return appDatabase.ingredientDao()
    }

    @Provides
    fun provideDurationDao(appDatabase: AppDatabase): DurationDao {
        return appDatabase.durationDao()
    }
}
