package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.manager.LocaleManager

class SettingsActivity : AppCompatActivity() {
    private lateinit var  localeManager: LocaleManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun attachBaseContext(base: Context?) {
        localeManager = LocaleManager(base)
        super.attachBaseContext(base?.let { localeManager.setLocale(it) })
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }
}