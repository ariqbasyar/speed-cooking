package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.ui.about

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.os.SystemClock
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.math.cos
import kotlin.math.sin

class OpenGLRenderer : GLSurfaceView.Renderer {


    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(1f, 1f, 0f, 1f)
        GLES20.glEnable(GLES20.GL_BLEND)
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        val time = SystemClock.uptimeMillis() % 2000L / 400L
        val red = sin(time.toFloat())*0.5f + 0.5f
        val green = cos(time.toFloat())*0.5f + 0.5f
        val blue = 0.0f
        GLES20.glClearColor(red, green, blue, 1f)
    }
}