package com.practice.headlines.util

import java.text.SimpleDateFormat
import java.util.*

fun getParsedDateString(date:String):String{
    val format= SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH
    )
    val outputFormat= SimpleDateFormat(
        "yyyy-MM-dd", Locale.ENGLISH
    )
    return outputFormat.format(format.parse(date))
}


fun getParsedDate(date:String):Date{
    val format= SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH
    )
    return format.parse(date)
}