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

            var wData:WData = weatherList.get(0)
            setData(view,wData);
        })

    }

    fun setData(view: View,wData:WData){

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
    }
}
