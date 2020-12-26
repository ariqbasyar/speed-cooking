package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.ui.about

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

class ObjLoader(context: Context, file: String) {
    private val numFaces: Int
    private val normals: FloatArray
    private val textureCoordinates: FloatArray
    private val positions: FloatArray
    val vertices: FloatArray

    fun getNumFaces(): Int {
        return numFaces
    }

    fun getNormals(): FloatArray {
        return normals
    }

    fun getTextureCoordinates(): FloatArray {
        return textureCoordinates
    }

    fun getPositions(): FloatArray {
        return positions
    }

    init {
        val vertices: Vector<Float> = Vector()
        val normals: Vector<Float> = Vector()
        val textures: Vector<Float> = Vector()
        val faces: Vector<String> = Vector()
        var reader: BufferedReader? = null
        try {
            val inputStream = InputStreamReader(context.assets.open(file))
            reader = BufferedReader(inputStream)

            // read file until EOF
            var line = reader.readLine()
            while (line != null) {
                val parts = line.split(" ").toTypedArray()
                line = reader.readLine()
                when (parts[0]) {
                    "v" -> {
                        // vertices
                        vertices.add(java.lang.Float.valueOf(parts[1]))
                        vertices.add(java.lang.Float.valueOf(parts[2]))
                        vertices.add(java.lang.Float.valueOf(parts[3]))
                    }
                    "vt" -> {
                        // textures
                        textures.add(java.lang.Float.valueOf(parts[1]))
                        textures.add(java.lang.Float.valueOf(parts[2]))
                    }
                    "vn" -> {
                        // normals
                        normals.add(java.lang.Float.valueOf(parts[1]))
                        normals.add(java.lang.Float.valueOf(parts[2]))
                        normals.add(java.lang.Float.valueOf(parts[3]))
                    }
                    "f" -> {
                        // faces: vertex/texture/normal
                        faces.add(parts[1])
                        faces.add(parts[2])
                        faces.add(parts[3])
                    }
                }
            }
        } catch (e: IOException) {
            Log.e("ASDASD", "msg: ${e.printStackTrace()}")
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    Log.e("ASDASD", "msg: ${e.printStackTrace()}")
                }
            }
        }
        numFaces = faces.size

        this.normals = FloatArray(numFaces * 3)
        textureCoordinates = FloatArray(numFaces * 2)
        positions = FloatArray(numFaces * 3)
        this.vertices = FloatArray(numFaces * 3)

        var positionIndex = 0
        var normalIndex = 0
        var textureIndex = 0

        for (face in faces) {
            val parts = face.split("/").toTypedArray()
            var index = 3 * (parts[0].toShort() - 1)
            positions[positionIndex++] = vertices[index++]
            positions[positionIndex++] = vertices[index++]
            positions[positionIndex++] = vertices[index]
            index = 2 * (parts[1].toShort() - 1)
            textureCoordinates[normalIndex++] = textures[index++]
            // NOTE: Bitmap gets y-inverted
            textureCoordinates[normalIndex++] = 1 - textures[index]
            index = 3 * (parts[2].toShort() - 1)
            this.normals[textureIndex++] = normals[index++]
            this.normals[textureIndex++] = normals[index++]
            this.normals[textureIndex++] = normals[index]
        }
    }
}
