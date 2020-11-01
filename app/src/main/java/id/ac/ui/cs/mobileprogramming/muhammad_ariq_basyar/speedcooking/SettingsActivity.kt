package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking

import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.ListView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import java.util.*

class SettingsActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun attachBaseContext(newBase: Context?) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(newBase)
        val lang = sharedPreferences.getString("lang", "en")
        Log.d("ASDASD", "attachBaseContext: $lang")
        super.attachBaseContext(SettingsActivity.ApplicationLanguageHelper.wrap(newBase!!, lang!!))
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }

    class ApplicationLanguageHelper(base: Context) : ContextThemeWrapper(base, R.style.AppTheme) {
        companion object {

            fun wrap(context: Context, language: String): ContextThemeWrapper {
                var newContext = context
                val config = newContext.resources.configuration
                if (language != "") {
                    val locale = Locale(language)

                    Log.d("ASDASD", "wrap: $locale")
                    Locale.setDefault(locale)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        setSystemLocale(config, locale)
                    } else {
                        setSystemLocaleLegacy(config, locale)
                    }
                    config.setLayoutDirection(locale)
                    newContext = context.createConfigurationContext(config)
                }
                return ApplicationLanguageHelper(newContext)
            }

            private fun setSystemLocaleLegacy(config: Configuration, locale: Locale) {
                config.locale = locale
            }

            private fun setSystemLocale(config: Configuration, locale: Locale) {
                config.setLocale(locale)
            }
        }
    }
}