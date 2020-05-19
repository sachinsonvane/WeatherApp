package com.cname.weatherapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cname.weatherapp.model.WData
import com.cname.weatherapp.viewmodel.WeathersDetailsViewModel
import com.cname.weatherapp.R;
import kotlinx.android.synthetic.main.fragment_second.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class WeatherDetails : Fragment() {

    var mWeatherDetailsViewModel:WeathersDetailsViewModel? = null
    var mWeatherDataList:List<WData>? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view);
        /*view.findViewById<Button>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }*/
    }

    fun init(view: View){

        mWeatherDetailsViewModel = ViewModelProvider(this)[WeathersDetailsViewModel::class.java]
        mWeatherDetailsViewModel!!.mWeathersData.observe(this, Observer { weatherList ->

            setData(view,weatherList);
        })

    }

    fun setData(view: View,list:List<WData>){

        var wData:WData = list.get(0)

        val updatedAtText = "Updated at: " + wData.dt
        val temp: String = wData.main.temp + "°C"
        val tempMin = "Min Temp: " + wData.main.temp_min + "°C"
        val tempMax = "Max Temp: " + wData.main.temp_max + "°C"
        val pressure: String = wData.main.pressure
        val humidity: String = wData.main.humidity

        val windSpeed: String = wData.wind.speed
        val weatherDescription: String = wData.weather.description
        val add: String = wData.city.name + ", " + wData.city.country

        addressTxt.setText(add)
        updatedAtTxt.setText(updatedAtText)
        statusTxt.setText(weatherDescription.toUpperCase())
        tempTxt.setText(temp)
        tempMinTxt.setText(tempMin)
        tempMaxTxt.setText(tempMax)
        windTxt.setText(windSpeed)
        pressureTxt.setText(pressure)
        humidityTxt.setText(humidity)

        /* 1 position data */
        var wData1:WData = list.get(1)
        val temp1: String = wData1.main.temp + "°C"
        val weatherDescription1: String = wData1.weather.description
        val updatedAtText1 = wData1.dt
        secondAtTxt.setText(updatedAtText1)
        secondStatusTxt.setText(weatherDescription1)
        secondTtempTxt.setText(temp1)

        /* 2 position data */
        var wData2:WData = list.get(2)
        val temp2: String = wData2.main.temp + "°C"
        val weatherDescription2: String = wData2.weather.description
        val updatedAtText2 = wData2.dt
        thirdAtTxt.setText(updatedAtText2)
        thirdStatusTxt.setText(weatherDescription2)
        thirdTtempTxt.setText(temp2)

        /* 3 position data */
        var wData3:WData = list.get(3)
        val temp3: String = wData3.main.temp + "°C"
        val weatherDescription3: String = wData3.weather.description
        val updatedAtText3 = wData3.dt
        fourAtTxt.setText(updatedAtText3)
        fourStatusTxt.setText(weatherDescription3)
        fourTtempTxt.setText(temp3)

        /* 4 position data */
        var wData4:WData = list.get(4)
        val temp4: String = wData4.main.temp + "°C"
        val weatherDescription4: String = wData4.weather.description
        val updatedAtText4 = wData4.dt
        fifthAtTxt.setText(updatedAtText4)
        fifthStatusTxt.setText(weatherDescription4)
        fifthTtempTxt.setText(temp4)
    }
}
