package com.tustel.io.tustel.domain.interfaces

import com.tustel.io.tustel.presentation.view.Tustel

/**
 * Created by Yoga C. Pranata on 04/10/20.
 * Android Engineer
 */
interface FastScrollStateChangeListener {
    fun onFastScrollStart(scroller: Tustel)
    fun onFastScrollStop(scroller: Tustel)
}