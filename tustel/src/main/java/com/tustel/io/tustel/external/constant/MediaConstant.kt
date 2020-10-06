package com.tustel.io.tustel.external.constant

import android.provider.MediaStore
import java.util.ArrayList

/**
 * Created by Yoga C. Pranata on 04/10/20.
 * Android Engineer
 */
object MediaConstant {

    val URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    val ORDER_BY = "${MediaStore.Images.Media.DATE_MODIFIED} DESC"
    val IMAGE_VIDEO_URI = MediaStore.Files.getContentUri("external")
    val IMAGE_VIDEO_ORDERBY = "${MediaStore.Images.Media.DATE_MODIFIED} DESC"

    val IMAGE_VIDEO_SELECTION = (
            "${MediaStore.Files.FileColumns.MEDIA_TYPE}=${MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE} OR "
            + "${MediaStore.Files.FileColumns.MEDIA_TYPE}=${MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO}")

    val IMAGE_SELECTION = "${MediaStore.Files.FileColumns.MEDIA_TYPE}=${MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE}"

    val PROJECTIONS = arrayOf(
        MediaStore.Images.Media.DATA,
        MediaStore.Images.Media._ID,
        MediaStore.Images.Media.DATE_ADDED,
        MediaStore.Images.Media.DATE_MODIFIED
    )

    val IMAGE_VIDEO_PROJECTION = arrayOf(
        MediaStore.Files.FileColumns.DATA,
        MediaStore.Files.FileColumns._ID,
        MediaStore.Files.FileColumns.PARENT,
        MediaStore.Files.FileColumns.DISPLAY_NAME,
        MediaStore.Files.FileColumns.DATE_ADDED,
        MediaStore.Files.FileColumns.DATE_MODIFIED,
        MediaStore.Files.FileColumns.MEDIA_TYPE,
        MediaStore.Files.FileColumns.MIME_TYPE,
        MediaStore.Files.FileColumns.TITLE
    )

}