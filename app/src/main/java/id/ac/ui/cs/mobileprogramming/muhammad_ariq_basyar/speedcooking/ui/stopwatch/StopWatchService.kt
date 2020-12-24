package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.ui.stopwatch

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.R
import kotlin.time.ExperimentalTime

const val DURATION_KEY = "stopwatch_duration"

class StopWatchService : Service() {

    private lateinit var elapsedTime: ElapsedTime
    private val broadcastIntent = Intent(this.javaClass.simpleName)
    val stopwatchServiceHandler = Handler()
    private var notified = false

    companion object {
        var bestDuration: Long = Long.MAX_VALUE
    }

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
        createNotificationChannel();
        elapsedTime = ElapsedTime()
        stopwatchServiceHandler.postDelayed(updater, 0)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Speedrun Reminder"
            val desc = "Reminder for ongoing speedccoking"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = desc
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.foreground_logo)
            .setContentTitle(resources.getString(R.string.notif_stopwatch_title))
            .setContentText(resources.getString(R.string.notif_stopwatch_content))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(this)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    @ExperimentalTime
    fun updateTimer() {
        elapsedTime.update()
        if (elapsedTime.getElapsedTime() > bestDuration && !notified) {
            sendNotification()
            notified = true
        }

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
