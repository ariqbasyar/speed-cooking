package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.converter

import android.net.Uri
import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun uriToString(uri: Uri): String = uri.toString()

    @TypeConverter
    fun stringToUri(string: String): Uri = Uri.parse(string)
}