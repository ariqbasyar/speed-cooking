package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.recipe

import android.net.Uri
import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    tableName = "recipes",
)
data class Recipe(
    val name: String,
    val procedure: String,
    @ColumnInfo(name = "image_uri") val imageUri: Uri,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val recipeId: Long = 0
): Parcelable
