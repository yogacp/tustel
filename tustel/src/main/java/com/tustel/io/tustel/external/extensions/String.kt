package com.tustel.io.tustel.external.extensions

/**
 * Created by Yoga C. Pranata on 04/10/20.
 * Android Engineer
 */
fun String?.notNullOrEmpty(f: (it: String) -> Unit) {
    if (this != null && this.isNotEmpty()) f(this)
}