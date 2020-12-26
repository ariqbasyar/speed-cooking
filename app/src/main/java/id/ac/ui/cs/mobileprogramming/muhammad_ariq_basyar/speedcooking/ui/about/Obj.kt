package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.ui.about

import android.content.Context
import android.opengl.GLES20
import android.opengl.Matrix
import android.os.SystemClock
import android.util.Log
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.util.*
import kotlin.properties.Delegates

class Obj(context: Context) {
    private var numFaces by Delegates.notNull<Int>()

    private var positions: FloatBuffer
    private var normals: FloatBuffer
    private var textureCoordinates: FloatBuffer
    private var vertices: FloatBuffer
    private var program: Int
    private val vertexShaderCode =
            "attribute vec4 pos;" +
            "uniform mat4 m;" +
            "void main() {" +
            "  gl_Position = m * pos;" +
            "}"
    private val fragmentShaderCode =
            "precision mediump float;" +
            "void main() {" +
            "  gl_FragColor = vec4(1.0, 0.5, 0.0, 1.0);" +
            "}"

    init {
        val objLoader = ObjLoader(context, "icon-3d.obj")

        numFaces = objLoader.getNumFaces()

        // Initialize the buffers.
        positions = ByteBuffer.allocateDirect(objLoader.getPositions().size * 4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        positions.put(objLoader.getPositions()).position(0)

        normals = ByteBuffer.allocateDirect(objLoader.getNormals().size * 4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        normals.put(objLoader.getNormals()).position(0)

        textureCoordinates =
            ByteBuffer.allocateDirect(objLoader.getTextureCoordinates().size * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer()
        textureCoordinates.put(objLoader.getTextureCoordinates()).position(0)

        vertices = ByteBuffer.allocateDirect(objLoader.vertices.size * 4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        vertices.put(objLoader.vertices).position(0)

        // Create vertex shader
        val vertexShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER)
        GLES20.glShaderSource(vertexShader, vertexShaderCode)

        // Create fragment shader
        val fragmentShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER)
        GLES20.glShaderSource(fragmentShader, fragmentShaderCode)

        // Compile shaders
        GLES20.glCompileShader(vertexShader)
        GLES20.glCompileShader(fragmentShader)

        // Create shader program
        program = GLES20.glCreateProgram().also { program ->
            GLES20.glAttachShader(program, vertexShader)
            GLES20.glAttachShader(program, fragmentShader)

            GLES20.glLinkProgram(program)
            GLES20.glUseProgram(program)
        }
    }

    fun draw() {
        // Set camera position and look at
        GLES20.glUseProgram(program)
        val projectionMatrix = FloatArray(16)
        val viewMatrix = FloatArray(16)
        val rotationMatrix = FloatArray(16)
        val productMatrix = FloatArray(16)

        val time = SystemClock.uptimeMillis() % 4000L
        val angle = 0.090f * time.toInt()
        Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, -3f, 0f, 0f, 0f, 0f, 1.0f, 0.0f)
        Matrix.setRotateM(rotationMatrix, 0, angle, angle, angle, angle)

        Matrix.multiplyMM(productMatrix, 0, projectionMatrix, 0, viewMatrix, 0)
        Matrix.multiplyMM(productMatrix, 0, productMatrix, 0, rotationMatrix, 0)

        val positionHandle = GLES20.glGetAttribLocation(program, "pos")
        GLES20.glEnableVertexAttribArray(positionHandle)
        GLES20.glVertexAttribPointer(
            positionHandle,
            4,
            GLES20.GL_FLOAT,
            false,
            12,
            normals
        )

        val matrix = GLES20.glGetUniformLocation(program, "m")
        GLES20.glUniformMatrix4fv(matrix, 1, false, productMatrix, 0)

//        for (face in 0 until numFaces) {
//            GLES20.glUniform4fv(colorHandle, 1, colors.get(face), 0)
//            indexBuffer.position(face * 6)
//            GLES20.glDrawElements(GLES20.GL_LINE_LOOP, 6, GLES20.GL_UNSIGNED_SHORT, indexBuffer)
//        }
        GLES20.glDrawElements(
            GLES20.GL_LINE_LOOP,
            numFaces * 3,
            GLES20.GL_UNSIGNED_SHORT,
            textureCoordinates
        )

        GLES20.glDisableVertexAttribArray(positionHandle)

    }
}
