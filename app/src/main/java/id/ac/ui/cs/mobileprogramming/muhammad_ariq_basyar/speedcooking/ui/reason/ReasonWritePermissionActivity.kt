package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.ui.reason

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.R

@AndroidEntryPoint
class ReasonWritePermissionActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.write_permission_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}