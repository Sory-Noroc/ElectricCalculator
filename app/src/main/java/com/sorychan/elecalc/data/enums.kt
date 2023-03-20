package com.sorychan.elecalc.data

enum class Power {
    mW, W, kW, MW
}

enum class Duration {
    MIN, H, D, M, Y
}

enum class Usage(val text: String) {
    MH("min/h"), HD("h/day"), DM("d/month")
}