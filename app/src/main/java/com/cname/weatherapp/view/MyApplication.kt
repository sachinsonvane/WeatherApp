package com.cname.weatherapp.view

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.cname.weatherapp.model.City

class MyApplication : Application() {

    val BASE_URL = "https://samples.openweathermap.org/data/2.5/forecast";
    var API = "439d4b804bc8187953eb36d2a8c26a02"
    val PRIVATE_MODE = 0
    val PREF_TITLE = "title"

    val PREF_ALL_RES_DATA = "all_res_data"

    val TIMEOUT = 7000
    var mCity:City? = null

    init {
        instance = this
    }

    companion object {
        private var instance: MyApplication? = null

        fun getInstance() : MyApplication {
            return instance as MyApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
    }

    public fun isNetworkAvailable(): Boolean {
        if (this.applicationContext == null) return false
        val connectivityManager = this.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

    override fun onTerminate() {
        super.onTerminate()
    }


}