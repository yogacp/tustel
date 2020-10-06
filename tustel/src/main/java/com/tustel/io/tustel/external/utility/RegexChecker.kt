package com.tustel.io.tustel.external.utility

/**
 * Created by Yoga C. Pranata on 04/10/20.
 * Android Engineer
 */
class RegexChecker {
    val GIF_PATTERN = "(.+?)\\.gif$".toRegex()
    fun checkGif(path: String) = path.matches(GIF_PATTERN)
}