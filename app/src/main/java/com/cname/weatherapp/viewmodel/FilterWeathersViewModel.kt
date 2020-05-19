package com.cname.weatherapp.viewmodel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cname.weatherapp.model.City
import kotlinx.coroutines.*
import java.io.InputStreamReader
import android.util.Log
import com.cname.weatherapp.model.Coord
import com.cname.weatherapp.view.MyApplication
import org.json.JSONArray
import java.io.IOException
import org.json.JSONObject

class FilterWeathersViewModel : ViewModel() {

    val mCitiesCompletableJob = Job()
    private val mCitiesCoroutineScope = CoroutineScope(Dispatchers.IO + mCitiesCompletableJob)
    private var mMutableLivegetCitiesData = MutableLiveData<List<City>>()
    val mCities: LiveData<List<City>>get() = getCities()

    fun getCities():MutableLiveData<List<City>>{

        mCitiesCoroutineScope.launch {

            withContext(Dispatchers.Main) {

                val cData: JSONObject = JSONObject(getAssetJsonData(MyApplication.getInstance().applicationContext))
                var cArr = JSONArray(cData);
                var list:ArrayList<City> = ArrayList()
                var size:Int = cArr.length()
                for (i in 0.. size-1) {
                    var item: JSONObject = cArr.getJSONObject(i)
                    var coord: JSONObject = item.getJSONObject("coord")
                    var lon:String = coord.getString("lon");
                    var lat:String = coord.getString("lat");

                    list.add(
                        City(
                            item.getLong("id"),
                            item.getString("name"),
                            item.getString("state"),
                            item.getString("country"), Coord(lon,lat)
                        )
                      )

                }
                mMutableLivegetCitiesData.value = list;
            }
        }
        return mMutableLivegetCitiesData;
    }

    fun getAssetJsonData(context: Context): String? {
        val json: String
        try {
            val inputStream = context.getAssets().open("citieslist.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.use { it.read(buffer) }
            json = String(buffer)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        // print the data
        Log.i("data", json)
        return json
    }
}


