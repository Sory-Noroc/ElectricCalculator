package com.sorychan.elecalc.data

import org.junit.Assert.*
import org.junit.Test

class MeasurementTest {

    @Test
    fun test_format_cost() {
        val testDevice = Device(100L, 2L, 5L, currency="$")
        assertEquals(testDevice.formatCost(), "0")
        assertEquals(testDevice.apply { cost = 999 }.formatCost(), "999")
        assertEquals(testDevice.apply { cost = 1001 }.formatCost(), "1K")
        assertEquals(testDevice.apply { cost = 10000 }.formatCost(), "10K")
        assertEquals(testDevice.apply { cost = 1000000 }.formatCost(), "1M")
        assertEquals(testDevice.apply { cost = 1010017 }.formatCost(), "1M")
        assertEquals(testDevice.apply { cost = 1000000000 }.formatCost(), "1B")
        assertEquals(testDevice.apply { cost = 999999999 }.formatCost(), "999M")
    }
}