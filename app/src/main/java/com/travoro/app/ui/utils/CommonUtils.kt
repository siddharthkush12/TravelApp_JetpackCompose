package com.travoro.app.ui.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit


fun calculateDaysLeft(tripDateString: String): Long? {
    return try {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val tripDate = format.parse(tripDateString) ?: return null
        val today = Date()
        val diff = tripDate.time - today.time
        TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
    } catch (e: Exception) {
        null
    }
}

fun getTimeOfDayGreeting(): String {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return when (hour) {
        in 0..11 -> "Good morning,"
        in 12..16 -> "Good afternoon,"
        else -> "Good evening,"
    }
}




fun formatDayOfWeek(
    dateString: String
): String {

    return try {
        val inputFormat = SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.getDefault()
        )
        val outputFormat = SimpleDateFormat(
            "EEE",
            Locale.getDefault()
        )
        val date = inputFormat.parse(dateString)
        outputFormat.format(date!!).uppercase(Locale.getDefault())
    } catch (e: Exception) {
        dateString
    }
}