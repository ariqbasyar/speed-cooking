package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import java.io.*
import java.util.*

class ImageUtils {
    companion object {
        private fun getBitmapFromExternal(
            imageUri: Uri,
            contentResolver: ContentResolver
        ): Bitmap? {
            var bitmap: Bitmap? = null

            try {
                bitmap = MediaStore.Images.Media.getBitmap(
                    contentResolver,
                    imageUri
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return bitmap
        }

        fun getBitmapFromCacheDirs(dir: String, imageUri: Uri, context: Context): Bitmap {
            val newDir = File(context.cacheDir, dir)
            val file = File(newDir, imageUri.path.toString())
            val uri = Uri.parse(file.absolutePath.toString())
            return getBitmapFromInternalStorage(uri)
        }

        private fun getBitmapFromInternalStorage(imageUri: Uri): Bitmap {
            val fis = FileInputStream(imageUri.path)
            val bitmap = BitmapFactory.decodeStream(fis)
            fis.close()
            return bitmap
        }

        fun saveFromExternalToInternalStorage(
            imageUri: Uri,
            filePath: File,
            contentResolver: ContentResolver
        ): File? {
            val bitmap: Bitmap? = getBitmapFromExternal(imageUri, contentResolver)
            return bitmap?.let { saveToInternalStorage(it, filePath) }
        }

        fun saveToInternalStorage(mBitmap: Bitmap, filePath: File, fileName: String): File? {
            val file = File(filePath, fileName)
            return goSaveFile(mBitmap, file)

        }

        fun saveToInternalStorage(mBitmap: Bitmap, filePath: File): File? {
            return saveToInternalStorage(mBitmap, filePath, "${UUID.randomUUID()}.jpg")
        }

        private fun goSaveFile(mBitmap: Bitmap, file: File): File? {
            try {
                val stream: OutputStream = FileOutputStream(file)

                mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                stream.flush()
                stream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return  file
        }
    }
}