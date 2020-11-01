package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.ingredient

import androidx.room.*
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.recipe.Recipe

@Entity(
    tableName = "ingredients",
    foreignKeys = [
        ForeignKey(entity = Recipe::class, parentColumns = ["id"], childColumns = ["recipe_id"])
    ],
    indices = [Index("recipe_id")]
)
data class Ingredient(
    @ColumnInfo(name = "recipe_id") val recipeId: Long,
    @ColumnInfo(name = "recipe_ingredient") val recipeIngredient: String,
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var ingredientId: Long = 0
}
