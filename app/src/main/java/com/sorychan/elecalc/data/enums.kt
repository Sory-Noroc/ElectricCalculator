package com.sorychan.elecalc.data

enum class Power(val unit: String) {
    mW("mW"), W("W"), kW("kW"), MW("MW")
}

enum class Duration(val unit: String) {
    MIN("min"), H("h"), D("days"), M("months"), Y("years")
}

enum class Usage(val unit: String) {
    MH("min/h"), HD("hours/day"), DM("days/month")
}