package com.nso.test.utils

import android.content.Context
import android.content.res.Resources
import com.nso.test.R
import com.nso.test.data.remote.model.StocksInfo
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs
import kotlin.math.roundToInt

private val density by lazy { Resources.getSystem().displayMetrics.density }

fun Int.dpToPx(): Int {
    return (this * density).roundToInt()
}

fun StocksInfo.refactoringCompany(): String {
    var newDelta: String = if (this.price.c?.minus(this.price.pc!!)!! < 0) {
        "-$" + String.format("%.2f", (abs(this.price.c!!.minus(this.price.pc!!))))
    } else {
        "+$" + String.format("%.2f", (abs(this.price.c!!.minus(this.price.pc!!))))
    }
    newDelta += " (" + String.format(
        "%.2f",
        (this.price.c!!.minus(this.price.pc!!)).div(this.price.c!!)
            .let { abs(it).times(100) }) + "%)"
    return newDelta
}

fun yesterday(context: Context): String {
    val cal = Calendar.getInstance()
    cal.add(Calendar.DATE, -3)
    val sdf = SimpleDateFormat(
        context.resources.getString(R.string.date_format_pattern),
        Locale.getDefault()
    )
    return String.format("%s", sdf.format(cal.time))
}

fun today(context: Context): String {
    val sdf = SimpleDateFormat(
        context.resources.getString(R.string.date_format_pattern),
        Locale.getDefault()
    )
    return String.format("%s", sdf.format(Date().time))
}

fun String.isPositive(): Boolean {
    return this.contains("+")
}