package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.ui.about

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet

class OpenGLView(context: Context, attributes: AttributeSet? = null): GLSurfaceView(context, attributes) {
    init {
        setEGLContextClientVersion(2)
        preserveEGLContextOnPause = true
        setRenderer(OpenGLRenderer())
    }
}