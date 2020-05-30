package com.cname.weatherapp.viewmodel

import android.icu.text.SimpleDateFormat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cname.weatherapp.api.HttpRestApi
import com.cname.weatherapp.model.*
import com.cname.weatherapp.view.MyApplication
import kotlinx.coroutines.*
import org.json.JSONObject
import java.util.*


class WeathersDetailsViewModel: ViewModel()  {

    val mWeathersCompletableJob = Job()
    private val mWeathersCoroutineScope = CoroutineScope(Dispatchers.IO + mWeathersCompletableJob)
    private var mMutableLivegetWeathersData = MutableLiveData<List<WData>>()

    val mWeathersData: LiveData<List<WData>> get() = getWeathersData()

    fun getWeathersData():MutableLiveData<List<WData>>{

        mWeathersCoroutineScope.launch {

            var url = MyApplication.getInstance().BASE_URL+"?id="+MyApplication.getInstance().mCity!!.id.toString()+"&appid="+MyApplication.getInstance().API
            //var url = MyApplication.getInstance().BASE_URL;

            var resData = HttpRestApi.createCorService().httpGet(url)


            withContext(Dispatchers.Main) {

                val wdata = JSONObject(resData)
                val listArr = wdata.getJSONArray("list")

                val city = wdata.getJSONObject("city")
                val cName = city.getString("name")
                val cCountry = city.getString("country")
                val cId = city.getInt("id").toString();
                val coord: JSONObject = city.getJSONObject("coord")
                var cityObj:City = City(cId,cName,"",cCountry, Coord(coord.getString("lon"),coord.getString("lat")))

                var list:ArrayList<WData> = ArrayList()
                var size:Int = listArr.length()
                for (i in 0.. size-1) {

                    val jsonObj = listArr.getJSONObject(i)
                    val main: JSONObject = jsonObj.getJSONObject("main")
                    val sys: JSONObject = jsonObj.getJSONObject("sys")
                    val wind: JSONObject = jsonObj.getJSONObject("wind")
                    val weather: JSONObject = jsonObj.getJSONArray("weather").getJSONObject(0)
                    val wId:String = weather.getInt("id").toString()
                    val wMain:String = weather.getString("main")
                    val wDescription:String = weather.getString("description")
                    val wIcon:String = weather.getString("icon")

                    val clouds = jsonObj.getJSONObject("clouds")
                    val cAll = clouds.getString("all")

                    val updatedAt = jsonObj.getLong("dt")
                    val updatedAtText = SimpleDateFormat(
                            "dd/MM/yyyy hh:mm a",
                            Locale.ENGLISH
                        ).format(
                            Date(updatedAt * 1000)
                        );
                    val temp = main.getString("temp")
                    val tempMin = main.getString("temp_min")
                    val tempMax = main.getString("temp_max")
                    val pressure = main.getString("pressure")
                    val sea_level = main.getString("sea_level")
                    val grnd_level = main.getString("grnd_level")
                    val humidity = main.getString("humidity")
                    var temp_kf = main.getString("temp_kf")

                    val pod = sys.getString("pod")

                    val windSpeed = wind.getString("speed")
                    val deg = wind.getString("deg")

                    val dt_txt = jsonObj.getString("dt_txt")

                    var wData:WData = WData(
                        updatedAtText,
                        Main(temp,tempMin,tempMax,pressure,sea_level,grnd_level,humidity,temp_kf),
                        Weather(wId,wMain,wDescription,wIcon),
                        Clouds(cAll),
                        Wind(windSpeed,deg),
                        Sys(pod),
                        dt_txt,
                        cityObj);
                    list.add(wData)
                }
                mMutableLivegetWeathersData.value = list
            }
        }

        return mMutableLivegetWeathersData;
    }
}