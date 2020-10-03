package com.tustel.io.tustel.data.model

import kotlinx.serialization.Serializable

/**
 * Created by Yoga C. Pranata on 03/10/20.
 * Android Engineer
 */
@Serializable
data class TustelImage(
    val headerData: String,
    val contentUrl: String,
    val url: String,
    val isSelected: Boolean,
    val scrollerDate: String,
    val mediaType: Int,
    val position: Int
)