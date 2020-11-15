package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.ui.stopwatch

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import kotlin.time.ExperimentalTime

const val DURATION_KEY = "stopwatch_duration"

class StopWatchService : Service() {

    private lateinit var elapsedTime: ElapsedTime
    private val broadcastIntent = Intent(this.javaClass.simpleName)
    val stopwatchServiceHandler = Handler()

    @ExperimentalTime
    val updater = object: Runnable {
        override fun run() {
            updateTimer()
            stopwatchServiceHandler.postDelayed(this, 1)
        }
    }

    @ExperimentalTime
    override fun onCreate() {
        super.onCreate()
        elapsedTime = ElapsedTime()
        stopwatchServiceHandler.postDelayed(updater, 0)
    }

    @ExperimentalTime
    fun updateTimer() {
        elapsedTime.update()

        val bundle = Bundle()
        bundle.putParcelable(DURATION_KEY, elapsedTime)
        broadcastIntent.putExtras(bundle)

        sendBroadcast(broadcastIntent)
    }


    @ExperimentalTime
    override fun onDestroy() {
        stopwatchServiceHandler.removeCallbacks(updater)
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}
