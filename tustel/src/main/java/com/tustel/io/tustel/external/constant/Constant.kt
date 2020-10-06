package com.tustel.io.tustel.external.constant

import android.provider.MediaStore

/**
 * Created by Yoga C. Pranata on 04/10/20.
 * Android Engineer
 */
object Constant {

    const val ERROR_LOG = "TUSTEL"
    const val BUBBLE_ANIM_DURATION = 1000
    const val SCROLL_HIDE_DELAY = 1000
    const val TRACK_SNAP_RANGE = 5
    const val MAX_VIDEO_DURATION = 40000
    const val SCROLLBAR_ANIM_DURATION = 500
    const val OPTIONS = "options"
    const val IMAGE_RESULTS = "image_results"

    object SCREEN_ORIENTATION {
        const val UNSET = -2
        const val UNSPECIFIED = -1
        const val LANDSCAPE = 0
        const val PORTRAIT = 1
        const val USER = 2
        const val BEHIND = 3
        const val SENSOR = 4
        const val NOSENSOR = 5
        const val SENSOR_LANDSCAPE = 6
        const val SENSOR_PORTRAIT = 7
        const val REVERSE_LANDSCAPE = 8
        const val REVERSE_PORTRAIT = 9
        const val FULL_SENSOR = 10
        const val USER_LANDSCAPE = 11
        const val USER_PORTRAIT = 12
        const val FULL_USER = 13
        const val LOCKED = 14
    }
}