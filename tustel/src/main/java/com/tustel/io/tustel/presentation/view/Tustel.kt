package com.tustel.io.tustel.presentation.view

import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewPropertyAnimator
import androidx.appcompat.app.AppCompatActivity
import com.tustel.io.R
import com.tustel.io.tustel.external.constant.Constant


/**
 * Created by Yoga C. Pranata on 04/10/20.
 * Android Engineer
 */
class Tustel : AppCompatActivity(), View.OnTouchListener {

    private val scrollbarHider = Runnable { hideScrollbar() }

    private lateinit var scrollbarAnimator: ViewPropertyAnimator
    private lateinit var bubbleAnimator: ViewPropertyAnimator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean {
        //TODO: Add core functionality
        return false
    }

    private fun hideScrollbar() {
//        val transX =
//            resources.getDimensionPixelSize(R.dimen.space_semi_small).toFloat()
//        scrollbarAnimator = mScrollbar.animate().translationX(transX).alpha(0f)
//            .setDuration(Constant.SCROLLBAR_ANIM_DURATION)
//            .setListener(object : AnimatorListenerAdapter() {
//                override fun onAnimationEnd(animation: Animator?) {
//                    super.onAnimationEnd(animation)
//                    mScrollbar.setVisibility(View.GONE)
//                    mScrollbarAnimator = null
//                }
//
//                override fun onAnimationCancel(animation: Animator?) {
//                    super.onAnimationCancel(animation)
//                    mScrollbar.setVisibility(View.GONE)
//                    mScrollbarAnimator = null
//                }
//            })
    }

}