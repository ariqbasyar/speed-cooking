package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.ui.stopwatch

import android.os.Parcel
import android.os.Parcelable
import java.util.concurrent.TimeUnit

class ElapsedTime() : Parcelable {
    private var startTime: Long = System.currentTimeMillis()
    private var elapsedTime: Long = 0

    constructor(parcel: Parcel) : this() {
        startTime = parcel.readLong()
        elapsedTime = parcel.readLong()
    }

    fun update() {
        elapsedTime = System.currentTimeMillis() - startTime
    }

    fun getElapsedTime(): Long {
        return elapsedTime
    }

    override fun toString(): String {
        return parseToString(elapsedTime)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(startTime)
        parcel.writeLong(elapsedTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ElapsedTime> {
        fun parseToString(elapsedTime: Long): String {
            val hours = String.format("%02d", getHours(elapsedTime))
            val minutes = String.format("%02d", getMinutes(elapsedTime))
            val seconds = String.format("%02d", getSeconds(elapsedTime))
            val milliseconds = String.format("%03d", getMilliseconds(elapsedTime))
            return "$hours:$minutes:$seconds $milliseconds"
        }

        private fun getHours(elapsedTime: Long): Long {
            return TimeUnit.MILLISECONDS.toHours(elapsedTime) % 100
        }

        private fun getMinutes(elapsedTime: Long): Long {
            return TimeUnit.MILLISECONDS.toMinutes(elapsedTime) % 60
        }

        private fun getSeconds(elapsedTime: Long): Long {
            return TimeUnit.MILLISECONDS.toSeconds(elapsedTime) % 60
        }

        private fun getMilliseconds(elapsedTime: Long): Long{
            return TimeUnit.MILLISECONDS.toMillis(elapsedTime) % 1000
        }

        override fun createFromParcel(parcel: Parcel): ElapsedTime {
            return ElapsedTime(parcel)
        }

        override fun newArray(size: Int): Array<ElapsedTime?> {
            return arrayOfNulls(size)
        }
    }
}