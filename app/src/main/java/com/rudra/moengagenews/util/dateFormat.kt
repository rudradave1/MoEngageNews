package com.rudra.moengagenews.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())

fun parseDate(dateString: String): Date? {
    return try {
        dateFormat.parse(dateString)
    } catch (e: ParseException) {
        // Handle parsing error
        null
    }
}