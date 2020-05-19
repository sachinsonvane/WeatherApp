package com.cname.weatherapp.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.cname.weatherapp.R;
import com.cname.weatherapp.model.City
import com.cname.weatherapp.viewmodel.FilterWeathersViewModel
import kotlinx.android.synthetic.main.fragment_first.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FilterWeathers : Fragment() , AdapterView.OnItemSelectedListener{

    var mFilterWeathersViewModel: FilterWeathersViewModel? = null
    var mCityInfoList = ArrayList()
    var mCities: List<City> = arrayListOf<City>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init();

        view.findViewById<Button>(R.id.goToWeatherDetailsBtn).setOnClickListener {
            findNavController().navigate(R.id.action_FilterWeathersFragment_to_WeatherDetailsFragment)
        }
    }

    fun init(){
        mFilterWeathersViewModel = ViewModelProvider(this)[FilterWeathersViewModel::class.java]
        mFilterWeathersViewModel!!.mCities.observe(this, Observer { cities ->

            mCities = cities;
            setAdapter(cities);
        })
    }

    fun setAdapter(cList:List<City>){


        var size:Int = cList.size
        for (i in 0.. size-1) {
            var cityObj = cList.get(i)
            mCityInfoList.add(cityObj.name+","+cityObj.state+","+cityObj.country)
        }

        var aa = ArrayAdapter(activity!!.baseContext, android.R.layout.simple_spinner_item, mCityInfoList)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        citiesSpinner.adapter = aa
        citiesSpinner.setSelection(0, false)
        citiesSpinner.onItemSelectedListener = this@FilterWeathers
        citiesSpinner.prompt = "Select your city"
        citiesSpinner.setPopupBackgroundResource(R.color.material_grey_600)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        MyApplication.getInstance().mCity = mCities.get(position);
    }

}
