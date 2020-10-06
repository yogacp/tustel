package com.tustel.io.tustel.external.utility

import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import android.os.Vibrator
import android.util.DisplayMetrics
import android.util.Log
import android.util.Size
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.tustel.io.R
import com.tustel.io.tustel.data.model.TustelImage
import com.tustel.io.tustel.external.constant.Constant
import com.tustel.io.tustel.external.constant.MediaConstant
import com.tustel.io.tustel.external.extensions.notNullOrEmpty
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt


/**
 * Created by Yoga C. Pranata on 04/10/20.
 * Android Engineer
 */
class TustelUtility {

    var HEIGHT = 0
    var WIDTH = 0

    private val pathDir: String? = null

    fun setupStatusBarHidden(appCompatActivity: AppCompatActivity) {
        val window = appCompatActivity.window
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )
        window.statusBarColor = Color.TRANSPARENT
    }

    fun showStatusBar(appCompatActivity: AppCompatActivity) {
        synchronized(appCompatActivity) {
            appCompatActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }

    fun hideStatusBar(appCompatActivity: AppCompatActivity) {
        synchronized(appCompatActivity) {
            appCompatActivity.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }

    fun getSoftButtonsBarSizePort(activity: Activity): Int {
        // getRealMetrics is only available with API 17 and +
        val metrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metrics)

        val usableHeight = metrics.heightPixels
        activity.windowManager.defaultDisplay.getRealMetrics(metrics)

        val realHeight = metrics.heightPixels

        return if (realHeight > usableHeight) {
            realHeight - usableHeight
        } else {
            0
        }
    }

    fun getStatusBarSizePort(check: AppCompatActivity): Int {
        var result = 0
        val res: Resources = check.baseContext.resources
        val resourceId: Int = res.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = check.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun getScreenSize(activity: Activity) {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        HEIGHT = displayMetrics.heightPixels
        WIDTH = displayMetrics.widthPixels
    }

    fun convertDpToPixel(dp: Float, context: Context): Float {
        val resources: Resources = context.resources
        val metrics: DisplayMetrics = resources.displayMetrics
        return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun convertPixelsToDp(px: Float, context: Context): Float {
        val resources: Resources = context.resources
        val metrics: DisplayMetrics = resources.displayMetrics
        return px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun getDateDifference(context: Context, calendar: Calendar): String? {
        val d: Date = calendar.getTime()
        val lastMonth: Calendar = Calendar.getInstance()
        val lastWeek: Calendar = Calendar.getInstance()
        val recent: Calendar = Calendar.getInstance()
        lastMonth.add(Calendar.DAY_OF_MONTH, -(Calendar.DAY_OF_MONTH))
        lastWeek.add(Calendar.DAY_OF_MONTH, -7)
        recent.add(Calendar.DAY_OF_MONTH, -2)

        if (calendar.before(lastMonth)) {
            return SimpleDateFormat("MMMM", Locale.getDefault()).format(d)
        } else if (calendar.after(lastMonth) && calendar.before(lastWeek)) {
            return context.resources.getString(R.string.tustel_last_month)
        } else return if (calendar.after(lastWeek) && calendar.before(recent)) {
            context.resources.getString(R.string.tustel_last_week)
        } else {
            context.resources.getString(R.string.tustel_recent)
        }
    }

    fun isNull(topChild: View?): Boolean {
        return topChild == null
    }

    fun getCursor(context: Context): Cursor? {
        return context.contentResolver.query(
            MediaConstant.URI,
            MediaConstant.PROJECTIONS,
            null,
            null,
            MediaConstant.ORDER_BY
        )
    }

    fun getImageVideoCursor(context: Context, excludeVideo: Boolean): Cursor? {
        val mediaSelection = if (excludeVideo) MediaConstant.IMAGE_SELECTION else MediaConstant.IMAGE_VIDEO_SELECTION
        return context.contentResolver
            .query(
                MediaConstant.IMAGE_VIDEO_URI,
                MediaConstant.IMAGE_VIDEO_PROJECTION,
                mediaSelection,
                null,
                MediaConstant.IMAGE_VIDEO_ORDERBY
            )
    }

    fun isViewVisible(view: View?): Boolean {
        return view != null && view.visibility == View.VISIBLE
    }

    fun showScrollbar(mScrollbar: View, context: Context): ViewPropertyAnimator? {
        val transX: Int = context.resources.getDimensionPixelSize(R.dimen.fastscroll_bubble_size)
        mScrollbar.translationX = transX.toFloat()
        mScrollbar.visibility = View.VISIBLE
        return mScrollbar.animate().translationX(0f).alpha(1f)
            .setDuration(Constant.SCROLLBAR_ANIM_DURATION.toLong())
            .setListener(object : AnimatorListenerAdapter() {})
    }

    fun cancelAnimation(animator: ViewPropertyAnimator?) {
        animator?.cancel()
    }

    fun manipulateVisibility(
        activity: AppCompatActivity?,
        slideOffset: Float,
        arrow_up: View,
        instantRecyclerView: RecyclerView,
        recyclerView: RecyclerView,
        status_bar_bg: View,
        topbar: View,
        clickme: View,
        sendButton: View,
        longSelection: Boolean
    ) {
        instantRecyclerView.alpha = 1 - slideOffset
        arrow_up.alpha = 1 - slideOffset
        clickme.alpha = 1 - slideOffset
        topbar.alpha = slideOffset
        recyclerView.alpha = slideOffset

        when {
            longSelection -> sendButton.alpha = 1 - slideOffset
            (1 - slideOffset) == 0f && instantRecyclerView.visibility == View.VISIBLE -> {
                instantRecyclerView.visibility = View.GONE
                arrow_up.visibility = View.GONE
                clickme.visibility = View.GONE
            }
            instantRecyclerView.visibility == View.GONE && (1 - slideOffset) > 0 -> {
                instantRecyclerView.visibility = View.VISIBLE
                arrow_up.visibility = View.VISIBLE
                clickme.visibility = View.VISIBLE

                if (longSelection) {
                    sendButton.clearAnimation()
                    sendButton.visibility = View.VISIBLE
                }
            }
            (slideOffset) > 0 && recyclerView.visibility == View.INVISIBLE -> {
                recyclerView.visibility = View.VISIBLE
                status_bar_bg.animate().translationY(0f).setDuration(200).start()
                topbar.visibility = View.VISIBLE
                activity?.let { showStatusBar(it) }
            }
            recyclerView.visibility == View.VISIBLE && (slideOffset) == 0f -> {
                activity?.let { hideStatusBar(it) }
                recyclerView.visibility = View.INVISIBLE
                topbar.visibility = View.GONE
                status_bar_bg.animate().translationY((-(status_bar_bg.height)).toFloat()).setDuration(550).start()
            }
        }
    }

    fun getValueInRange(min: Int, max: Int, value: Int): Int {
        val minimum = max(min, value)
        return min(minimum, max)
    }

    fun vibe(c: Context, l: Long) {
        (c.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).vibrate(l)
    }

    fun writeImage(
        bitmap: Bitmap,
        path: String?,
        quality: Int,
        newWidth: Int,
        newHeight: Int
    ): File? {

        var photo: File? = null

        path?.notNullOrEmpty { pathString ->
            var mBitmap = bitmap
            var mNewWidth = newWidth
            var mNewHeight = newHeight

            val dir = File(Environment.getExternalStorageDirectory(), pathString)

            if (!dir.exists()) {
                dir.mkdirs()
            }

            photo = File(
                dir, ("IMG_${SimpleDateFormat("yyyyMMdd_HHmmSS", Locale.ENGLISH).format(Date())}.jpg")
            )

            if (photo?.exists() == true) {
                photo?.delete()
            }

            if (newWidth == 0 && newHeight == 0) {
                mNewWidth = bitmap.width / 2
                mNewHeight = bitmap.height / 2
            }

            mBitmap = getResizedBitmap(bitmap, newWidth, newHeight)

            try {
                photo?.let {
                    val fos = FileOutputStream(it.path)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fos)
                    fos.close()
                }
            } catch (e: Exception) {
                Log.e(Constant.ERROR_LOG, "Error while creating the image", e)
            }
        }

        return photo
    }

    fun getResizedBitmap(bitmap: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val scaleWidth = (newWidth.toFloat()) / width
        val scaleHeight = (newHeight.toFloat()) / height
        val matrix = Matrix()

        matrix.postScale(scaleWidth, scaleHeight)

        val resizedBitmap = Bitmap.createBitmap(
            bitmap, 0, 0, width, height, matrix, false
        )

        return resizedBitmap.copy(Bitmap.Config.RGB_565, false)
    }

    fun getScaledBitmap(maxWidth: Int, rotatedBitmap: Bitmap): Bitmap? {
        try {
            val newHeight = (rotatedBitmap.height * (512.0 / rotatedBitmap.width)).toInt()
            return Bitmap.createScaledBitmap(rotatedBitmap, maxWidth, newHeight, true)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun rotate(scaledBitmap: Bitmap, i: Float): Bitmap? {
        if (i.toInt() == 0) {
            return scaledBitmap
        }

        val matrix = Matrix()
        matrix.preRotate(-i)

        return Bitmap.createBitmap(
            scaledBitmap,
            0,
            0,
            scaledBitmap.width,
            scaledBitmap.height,
            matrix,
            false
        )
    }

    fun getFingerSpacing(event: MotionEvent): Float {
        try {
            val x = event.getX(0) - event.getX(1)
            val y = event.getY(0) - event.getY(1)
            return sqrt(x * x + y * y.toDouble()).toFloat()
        } catch (e: Exception) {
            Log.e(Constant.ERROR_LOG, "FingerSpacing ->" + e.message)
            return 0f
        }
    }

    fun containsName(list: ArrayList<TustelImage?>, url: String?): Boolean {
        for (output: TustelImage? in list) {
            return  (output != null && output.url == url)
        }
        return false
    }

    fun scanPhoto(pix: Context, photo: File) {
        val scanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val contentUri: Uri = Uri.fromFile(photo)
        scanIntent.data = contentUri
        pix.sendBroadcast(scanIntent)
    }

    fun gcd(p: Int, q: Int): Int {
        return if (q == 0) p else gcd(q, p % q)
    }

    fun ratio(a: Int, b: Int): Size? {
        val gcd = gcd(a, b)
        return if (a > b) {
            Size(a / gcd, b / gcd)
        } else {
            Size(b / gcd, a / gcd)
        }
    }
}