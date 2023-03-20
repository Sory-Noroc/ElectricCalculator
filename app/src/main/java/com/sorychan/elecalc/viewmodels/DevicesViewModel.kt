package com.sorychan.elecalc.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sorychan.elecalc.data.Device

private const val TAG = "DevicesViewModel"

class DevicesViewModel : ViewModel() {

    private val _deviceList = MutableLiveData<MutableList<Device>>().apply {
        value = mutableListOf()
    }
    val deviceList: LiveData<MutableList<Device>> = _deviceList

    val totalCost = MutableLiveData<Long>(0)

    val totalConsumption = MutableLiveData(0.0)

    fun addDevice(device: Device) {
        val list = deviceList.value ?: mutableListOf()
        list.add(device)
        totalCost.value = totalCost.value?.plus(device.cost)
        totalConsumption.value = totalConsumption.value?.plus(device.consumption)
        _deviceList.value = list
        Log.i(TAG, "Added to list: ${_deviceList.value} with consumption ${totalConsumption.value} and cost of ${totalConsumption.value}")
    }
}