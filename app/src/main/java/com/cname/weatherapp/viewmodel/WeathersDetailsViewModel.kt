package com.cname.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cname.weatherapp.model.City
import com.cname.weatherapp.model.WData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class WeathersDetailsViewModel: ViewModel()  {

    val mWeathersCompletableJob = Job()
    private val mWeathersCoroutineScope = CoroutineScope(Dispatchers.IO + mWeathersCompletableJob)
    private var mMutableLivegetWeathersData = MutableLiveData<List<WData>>()

    val mWeathersData: LiveData<List<WData>> get() = getWeathersData()

    fun getWeathersData():MutableLiveData<List<WData>>{

        return mMutableLivegetWeathersData;
    }
}