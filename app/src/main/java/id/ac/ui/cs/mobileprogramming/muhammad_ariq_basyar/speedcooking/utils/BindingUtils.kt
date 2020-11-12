@file:JvmName("BindingUtils")
package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.utils

const val TRIM_TOO_LONG_PROCEDURE = 40

fun trimProcedure(procedure: String): String {
    if (procedure.length < TRIM_TOO_LONG_PROCEDURE + 1) {
        return procedure
    }
    return procedure.substring(0, TRIM_TOO_LONG_PROCEDURE) + "..."
}
