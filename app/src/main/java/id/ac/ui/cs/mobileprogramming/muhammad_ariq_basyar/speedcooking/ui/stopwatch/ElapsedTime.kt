package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.ui.stopwatch

import android.os.Parcel
import android.os.Parcelable
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.ui.stopwatch.parser.Parser

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
            return Parser.parseToString(elapsedTime)
        }

        override fun createFromParcel(parcel: Parcel): ElapsedTime {
            return ElapsedTime(parcel)
        }

        override fun newArray(size: Int): Array<ElapsedTime?> {
            return arrayOfNulls(size)
        }
    }
}