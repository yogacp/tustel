package com.tustel.io.tustel.domain.interfaces

import android.view.View
import com.tustel.io.tustel.data.model.TustelImage

/**
 * Created by Yoga C. Pranata on 04/10/20.
 * Android Engineer
 */
interface OnSelectionListener {
    fun onClick(image: TustelImage, view: View, position: Int)
    fun onLongClick(image: TustelImage, view: View, position: Int)
}