package com.sorychan.elecalc.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sorychan.elecalc.data.Duration
import com.sorychan.elecalc.data.Power

class DevicesViewModel : ViewModel() {

    private val _powerUnit = MutableLiveData<Power>().apply {
        value = Power.W
    }
    val powerUnit: LiveData<Power> = _powerUnit

    private val _durationUnit = MutableLiveData<Duration>().apply {
        value = Duration.M
    }
    val durationUnit: LiveData<Duration> = _durationUnit

    private val _usageUnit = MutableLiveData<Duration>().apply {
        value = Duration.M
    }
    val usageUnit: LiveData<Duration> = _durationUnit
}