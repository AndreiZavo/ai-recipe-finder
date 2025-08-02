package com.example.recipefinder.ui.utils

fun formatDuration(minutes: Int): String {
    val days = minutes / (60 * 24)
    val hours = (minutes % (60 * 24)) / 60
    val remainingMinutes = minutes % 60

    return buildString {
        if (days > 0) append("$days d ")
        if (hours > 0 || days > 0) append("$hours h ")
        if (remainingMinutes > 0 || (days == 0 && hours == 0)) append("$remainingMinutes min.")
    }.trim()
}