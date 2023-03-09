package com.sorychan.elecalc.data

import com.sorychan.elecalc.viewmodels.DevicesViewModel

class Device(val name: String = "Device",
             val power: Long, val powerUnit: Power = Power.kW,
             val duration: Long, val durationUnit: Duration = Duration.D,
             val usage: Long, val usageUnit: Usage = Usage.HD) {

    /** Keys:
     * P = power
     * D = duration
     * U = usage
     */
    private lateinit var defaults: Map<String, Double>

    init {
        setDefaults()
    }

    private fun setDefaults() {
        val temp = mutableMapOf<String, Double>()

        val defPower: Double = when(powerUnit) {
            Power.MW -> power.toDouble() * 1000
            Power.kW -> power.toDouble()
            Power.W  -> power.toDouble() / 1000
            Power.mW -> power.toDouble() / 1_000_000
        }
        temp["P"] = defPower

        val defDuration: Double = when(durationUnit) {
            Duration.MIN-> duration.toDouble() / 1440
            Duration.H  -> duration.toDouble() / 60
            Duration.D  -> duration.toDouble()
            Duration.M  -> duration.toDouble() * 30
            Duration.Y  -> duration.toDouble() * 365
        }
        temp["D"] = defDuration

        val defUsage: Double = when(usageUnit) {
            Usage.MH -> 0.4 * usage
            Usage.HD -> 1.0 * usage
            Usage.DM -> 0.8 * usage
        }
        temp["U"] = defUsage
        defaults = temp
    }
}