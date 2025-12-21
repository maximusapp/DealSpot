package com.app.dealspot.presentation.utils

import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

const val mySQLDateFormat: String = "yyyy-MM-dd HH:mm:ss"

/**
 * Gets the current date and time formatted as "yyyy-MM-dd HH:mm:ss"
 * @return Current date time string in MySQL format
 */
@OptIn(ExperimentalTime::class)
fun getCurrentDateTime(): String {
    val now = Clock.System.now()
    val localDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
    
    return buildString {
        append(localDateTime.year.toString().padStart(4, '0'))
        append("-")
        append(localDateTime.month.number.toString().padStart(2, '0'))
        append("-")
        append(localDateTime.day.toString().padStart(2, '0'))
        append(" ")
        append(localDateTime.hour.toString().padStart(2, '0'))
        append(":")
        append(localDateTime.minute.toString().padStart(2, '0'))
        append(":")
        append(localDateTime.second.toString().padStart(2, '0'))
    }
}
