package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.recipe

import androidx.room.*

@Entity(
    tableName = "recipes",
)
data class Recipe(
    val name: String,
    val procedure: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var recipeId: Long = 0
}
