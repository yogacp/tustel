package com.tustel.io.tustel.external.utility

import android.content.Context
import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.tustel.io.tustel.domain.interfaces.StickyHeaderInterface


/**
 * Created by Yoga C. Pranata on 04/10/20.
 * Android Engineer
 */
class HeaderItemDecoration(private val context: Context, private val listener: StickyHeaderInterface) : ItemDecoration() {

    private val tustelUtility = TustelUtility()

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(canvas, parent, state)

        val topChild: View = parent.getChildAt(0)
        if (tustelUtility.isNull(topChild)) {
            return
        }

        val topChildPosition = parent.getChildAdapterPosition(topChild)
        if (topChildPosition == RecyclerView.NO_POSITION) {
            return
        }

        val currentHeader: View = getHeaderViewForItem(topChildPosition, parent)

        currentHeader.setPadding(
            (currentHeader.paddingLeft - TustelUtility().convertPixelsToDp(5f, context)).toInt(),
            currentHeader.paddingTop,
            currentHeader.paddingRight,
            currentHeader.paddingBottom
        )
        fixLayoutSize(parent, currentHeader)

        val contactPoint: Int = currentHeader.bottom
        val childInContact: View? = getChildInContact(parent, contactPoint)

        childInContact?.let {
            if (listener.isHeader(parent.getChildAdapterPosition(it))) {
                moveHeader(canvas, currentHeader, childInContact)
                return
            }
        }

        drawHeader(canvas, currentHeader)
    }

    private fun getHeaderViewForItem(itemPosition: Int, parent: RecyclerView): View {
        val headerPosition = listener.getHeaderPositionForItem(itemPosition)
        val layoutResId = listener.getHeaderLayout(headerPosition)
        val header: View = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        listener.bindHeaderData(header, headerPosition)
        return header
    }

    private fun drawHeader(canvas: Canvas, header: View) {
        canvas.save()
        canvas.translate(0f, 0f)
        header.draw(canvas)
        canvas.restore()
    }

    private fun moveHeader(canvas: Canvas, currentHeader: View, nextHeader: View?) {
        canvas.save()
        nextHeader?.top?.minus(currentHeader.height.toFloat())?.let {
            canvas.translate(0f,
                it
            )
        }
        currentHeader.draw(canvas)
        canvas.restore()
    }

    private fun getChildInContact(parent: RecyclerView, contactPoint: Int): View? {
        var childInContact: View? = null
        for (i in 0 until parent.childCount) {
            val child: View = parent.getChildAt(i)
            if (child.bottom > contactPoint && child.top <= contactPoint) {
                childInContact = child
                break
            }
        }
        return childInContact
    }

    private fun fixLayoutSize(parent: ViewGroup, view: View) {

        /**
         * For Parent
         */
        val widthSpec: Int = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
        val heightSpec: Int = View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED)

        /**
         * For Child headers
         */
        val childWidthSpec = ViewGroup.getChildMeasureSpec(
            widthSpec,
            parent.paddingLeft + parent.paddingRight,
            view.layoutParams.width
        )

        val childHeightSpec = ViewGroup.getChildMeasureSpec(
            heightSpec,
            parent.paddingTop + parent.paddingBottom,
            view.layoutParams.height
        )

        view.measure(childWidthSpec, childHeightSpec)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    }
}