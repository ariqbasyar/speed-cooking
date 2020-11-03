package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.duration

import android.os.Parcelable
import androidx.room.*
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.recipe.Recipe
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    tableName = "durations",
    foreignKeys = [
        ForeignKey(entity = Recipe::class, parentColumns = ["id"], childColumns = ["recipe_id"])
    ],
    indices = [Index("recipe_id")]
)
data class Duration(
    @ColumnInfo(name = "recipe_id") val recipeId: Long,
    @ColumnInfo(name = "recipe_duration") val recipeDuration: Long,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var durationId: Long = 0
): Parcelable
