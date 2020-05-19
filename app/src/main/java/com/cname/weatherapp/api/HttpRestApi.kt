package com.cname.weatherapp.api

import android.content.SharedPreferences
import com.cname.weatherapp.view.MyApplication
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class HttpRestApi {

    var CITY = "dhaka,bd"
    var API = "8118ed6ee68db2debfaaa5a44c832918"
// String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&units=metric&appid=" + API);
//
    //https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&units=metric&appid=" + API

    companion object {

        fun createCorService(): HttpRestApi {

            return HttpRestApi();
        }
    }

    fun httpGet(urlStr:String): String {

        val inputStream: InputStream
        var result:String = ""

        // create URL
        val url: URL = URL(urlStr)

        // create HttpURLConnection
        val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
        //conn.setReadTimeout(MyApplication.getInstance().TIMEOUT)
        // conn.setConnectTimeout(MyApplication.getInstance().TIMEOUT)
        /* conn.requestMethod = "GET"
         conn.instanceFollowRedirects = false
         conn.doOutput = true
         conn.doInput = true
         conn.useCaches = false
         conn.setRequestProperty("Content-Type", "application/json; charset=utf-8")*/

        try {
            conn.connect()
            if (conn.responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = conn.inputStream
                if(inputStream != null) {
                    val stream = BufferedInputStream(inputStream)
                    result = readStream(stream)
                    if(result!=null && !result.equals("")){
                        val context = MyApplication.getInstance().applicationContext;
                        val sharedPrefAllResData: SharedPreferences =
                            context.getSharedPreferences(MyApplication.getInstance().PREF_ALL_RES_DATA, MyApplication.getInstance().PRIVATE_MODE)
                        val editorAllResData = sharedPrefAllResData.edit()
                        editorAllResData.putString(MyApplication.getInstance().PREF_ALL_RES_DATA, result)
                        editorAllResData.apply()
                    }
                }else {
                    result = ""
                }
            } else {
                println("ERROR ${conn.responseCode}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            conn.disconnect()
        }
        return result
    }
    fun readStream(inputStream: BufferedInputStream): String {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        bufferedReader.forEachLine { stringBuilder.append(it) }
        return stringBuilder.toString()
    }
}