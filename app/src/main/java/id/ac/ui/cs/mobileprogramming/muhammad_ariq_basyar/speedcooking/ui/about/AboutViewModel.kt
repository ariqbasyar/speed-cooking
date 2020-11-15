package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.ui.about

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.*

const val API_URL = "https://ariqbasyar.id/about-me.json"

class AboutViewModel(application: Application): AndroidViewModel(application) {

    val isFetched = MutableLiveData<Boolean>()
    val aboutMe = MutableLiveData<String>()

    init {
        fetchAboutMe()
    }

    private fun fetchAboutMe() {
        val currentLocale: Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getApplication<Application>().resources.configuration.locales.get(0)
        } else {
            getApplication<Application>().resources.configuration.locale
        }
        val lang = when(currentLocale.displayLanguage) {
            "Inggris" -> "in"
            else -> "en"
        }
        val request = Request
            .Builder()
            .url("$API_URL?lang=$lang")
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(
                    AboutViewModel::class.java.simpleName,
                    "onResponse: Failed to execute request"
                )
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                isFetched.postValue(true)
                var data: String
                val jsonData: JSONObject
                if (body != null) {
                    try {
                        jsonData = JSONObject(body)
                        data = if (!jsonData.isNull("data")) {
                            jsonData.getString("data")
                        } else {
                            "Failed to get data"
                        }
                    } catch (e: JSONException) {
                        data = "Failed to get data"
                    }
                } else {
                    data = "Failed to get data"
                }
                aboutMe.postValue(data)
            }
        })
    }
}