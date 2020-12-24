package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.ui.stopwatch.parser

class Parser {
    companion object {
        init {
            System.loadLibrary("converter-lib")
        }
        external fun parseToString(duration: Long): String
    }
}