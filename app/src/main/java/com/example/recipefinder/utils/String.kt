package com.example.recipefinder.utils

fun String.withoutEmoji(): String {
    return this.filter {
        it.isWhitespace() || it.isLetterOrDigit() || ("!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?".contains(
            it
        ))
    }
}