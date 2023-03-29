package com.sorychan.elecalc.data

class Device(val power: Long,
             val duration: Long,
             val usage: Long,
             val powerUnit: Power = Power.kW,
             val durationUnit: Duration = Duration.D,
             val usageUnit: String = Usage.HD.text,
             val name: String = "Device",
             var consumption: Double = 0.0,
             var cost: Long = 0) {

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
        temp["Power"] = defPower

        val defDuration: Double = when(durationUnit) {
            Duration.MIN-> duration.toDouble() / 1440
            Duration.H  -> duration.toDouble() / 60
            Duration.D  -> duration.toDouble()
            Duration.M  -> duration.toDouble() * 30
            Duration.Y  -> duration.toDouble() * 365
        }
        temp["Days"] = defDuration

        val defUsage: Double = when(usageUnit) {
            Usage.MH.text -> 0.4 * usage
            Usage.HD.text -> 1.0 * usage
            Usage.DM.text -> 0.8 * usage
            else -> usage.toDouble()
        }
        temp["Usage"] = defUsage
        defaults = temp
    }

    fun formatCost(): String {
        var doubleCost = cost.toDouble()
        var iterations = 0
        while (doubleCost / 1000 >= 1) {
            iterations++
            doubleCost /= 1000
        }
        return when (iterations) {
            0 -> cost.toString()
            1 -> doubleCost.toLong().toString() + "K"
            2 -> doubleCost.toLong().toString() + "M"
            3 -> doubleCost.toLong().toString() + "B"
            4 -> doubleCost.toLong().toString() + "T"
            5 -> doubleCost.toLong().toString() + "Q"
            else -> cost.toString()
        }
    }

    fun calculateCost(price: Long) {
        // The consumption is measured in kWh
        consumption = defaults["Power"]?.times(
            defaults["Days"]?.times(
                defaults["Usage"] ?: 0.0
            ) ?: 0.0
        ) ?: 0.0
        cost = (price * consumption).toLong()
    }
}