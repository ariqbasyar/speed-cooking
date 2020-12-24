package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.R
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.*

const val API_URL = "https://ariqbasyar.id/about-me.json"

class AboutViewModel(application: Application): AndroidViewModel(application) {

    val isFetched = MutableLiveData<Boolean>()
    val aboutMe = MutableLiveData<String>()
    private val context = getApplication<Application>().applicationContext
    private val resources = getApplication<Application>().resources

    init {
        fetchAboutMe()
    }

    private fun fetchAboutMe() {
        val message = context?.let { messageConnection(it) }
        if (message != "") {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            return
        }
        val currentLocale: Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getApplication<Application>().resources.configuration.locales.get(0)
        } else {
            getApplication<Application>().resources.configuration.locale
        }
        val lang = currentLocale.language
        val request = Request
            .Builder()
            .url("$API_URL?lang=$lang")
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(
                    AboutViewModel::class.java.simpleName,
                    "onFailure: Failed to execute request"
                )
                aboutMe.postValue("Failed to get data")
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

    private fun messageConnection(context: Context): String {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            return resources.getString(if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    R.string.empty_string
                } else {
                    R.string.only_wifi
                }
            } else {
                R.string.have_network_connection_q
            })
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return resources.getString(if (activeNetworkInfo != null) {
                if (activeNetworkInfo.type == NetworkCapabilities.TRANSPORT_WIFI) {
                    R.string.empty_string
                } else {
                    R.string.only_wifi
                }
            } else {
                R.string.have_network_connection_q
            })
        }
    }
}