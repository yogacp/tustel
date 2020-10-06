package com.tustel.io.tustel.external.utility

import com.tustel.io.tustel.external.constant.Constant
import kotlinx.serialization.Serializable

/**
 * Created by Yoga C. Pranata on 04/10/20.
 * Android Engineer
 */
@Serializable
class TustelOptions {

    private var tustelOptions: TustelOptions? = null
    private var count = 1
    private var requestCode = 0
    private var spanCount = 4
    private val height = 0
    private val width = 0
    private var frontfacing = false
    private var excludeVideos = false
    private var path = "Tustel/Images"
    private var videoDurationLimitinSeconds = 40
    private var preSelectedUrls: ArrayList<String> = ArrayList()

    init {
        tustelOptions = this
    }

    @ScreenOrientation
    private var screenOrientation = Constant.SCREEN_ORIENTATION.UNSPECIFIED

    @Retention(AnnotationRetention.SOURCE)
    annotation class ScreenOrientation

    fun getVideoDurationLimitinSeconds(): Int {
        return videoDurationLimitinSeconds
    }

    fun setVideoDurationLimitinSeconds(videoDurationLimitinSececonds: Int): TustelOptions {
        videoDurationLimitinSeconds = videoDurationLimitinSececonds
        return this
    }

    fun getPreSelectedUrls(): ArrayList<String> {
        return preSelectedUrls
    }

    fun setPreSelectedUrls(preSelectedUrls: ArrayList<String>): TustelOptions {
        check()
        this.preSelectedUrls = preSelectedUrls
        return this
    }

    fun isExcludeVideos(): Boolean {
        return excludeVideos
    }

    fun setExcludeVideos(excludeVideos: Boolean): TustelOptions {
        this.excludeVideos = excludeVideos
        return this
    }

    fun getHeight(): Int {
        return height
    }

    fun getWidth(): Int {
        return width
    }

    fun isFrontfacing(): Boolean {
        return frontfacing
    }

    fun setFrontfacing(frontfacing: Boolean): TustelOptions {
        this.frontfacing = frontfacing
        return this
    }

    private fun check() {
        if (tustelOptions == null) {
            throw NullPointerException("call init() method to initialise Options class")
        }
    }

    fun getCount(): Int {
        return count
    }

    fun setCount(count: Int): TustelOptions {
        check()
        this.count = count
        return this
    }

    fun getRequestCode(): Int {
        if (requestCode == 0) {
            throw NullPointerException("requestCode in Options class is null")
        }
        return requestCode
    }

    fun setRequestCode(requestcode: Int): TustelOptions {
        check()
        requestCode = requestcode
        return this
    }

    fun getPath(): String {
        return path
    }

    fun setPath(path: String): TustelOptions {
        check()
        this.path = path
        return this
    }

    fun getScreenOrientation(): Int {
        return screenOrientation
    }

    fun setScreenOrientation(@ScreenOrientation screenOrientation: Int): TustelOptions {
        check()
        this.screenOrientation = screenOrientation
        return this
    }

    fun getSpanCount(): Int {
        return spanCount
    }

    fun setSpanCount(spanCount: Int): TustelOptions {
        check()
        this.spanCount = spanCount
        require(spanCount !in 6 downTo 0) { "Span count can not be set below 0 or more than 5" }
        return this
    }
}