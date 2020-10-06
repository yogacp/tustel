package com.tustel.io.tustel.external.utility

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.AsyncTask
import android.provider.MediaStore
import com.tustel.io.tustel.data.model.TustelImage
import com.tustel.io.tustel.data.model.TustelImages
import com.tustel.io.tustel.external.constant.MediaConstant
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by Yoga C. Pranata on 04/10/20.
 * Android Engineer
 */
class VideoFetcher(val context: Context) : AsyncTask<Cursor, Void, TustelImages>() {

    var header = ""
    var startingCount = 0
    private val images = ArrayList<TustelImage>()
    private val selectionList = ArrayList<TustelImage>()
    private val preSelectedUrls = ArrayList<String>()
    private val tustelUtility = TustelUtility()

    override fun doInBackground(vararg cursors: Cursor?): TustelImages {
        cursors.first()?.let { cursor ->
            try {
                val date = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATE_ADDED)
                val data = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA)
                val mediaType = cursor.getColumnIndex(MediaStore.Files.FileColumns.MEDIA_TYPE)
                val contentUrl = cursor.getColumnIndex(MediaStore.Files.FileColumns._ID)
                val videoDate = cursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED)

                var limit = 100
                if (cursor.count < limit) {
                    limit = cursor.count - 1
                }
                cursor.move(limit)

                synchronized(context) {
                    var position = startingCount
                    for (i in limit until cursor.count) {
                        cursor.moveToNext()
                        val path: Uri = Uri.withAppendedPath(
                            MediaConstant.IMAGE_VIDEO_URI, "" + cursor.getInt(
                                contentUrl
                            )
                        )
                        val calendar: Calendar = Calendar.getInstance()
                        val dateVideo = videoDate

                        calendar.timeInMillis = cursor.getLong(date) * 1000
                        calendar.timeInMillis = cursor.getLong(dateVideo) * 1000

                        val dateDifference: String? = tustelUtility.getDateDifference(
                            context,
                            calendar
                        )

                        val type = cursor.getInt(mediaType)

                        if (!header.equals("" + dateDifference, ignoreCase = true)) {
                            header = "" + dateDifference
                            position += 1
                            images.add(
                                TustelImage(
                                    "" + dateDifference,
                                    "",
                                    "",
                                    "",
                                    type
                                )
                            )
                        }

                        val image = TustelImage(
                            "" + header,
                            "" + path,
                            cursor.getString(data),
                            "" + position,
                            type
                        )

                        image.position = position
                        if (preSelectedUrls.contains(image.url)) {
                            image.isSelected = true
                            selectionList.add(image)
                        }

                        position += 1
                        images.add(image)
                    }

                    cursor.close()
                }
            } catch (error: Exception) {
                error.printStackTrace()
            }
        }

        return TustelImages(images, selectionList)
    }

}