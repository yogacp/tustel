package com.tustel.io.tustel.external.utility

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.tustel.io.tustel.domain.interfaces.OnWorkFinish


/**
 * Created by Yoga C. Pranata on 04/10/20.
 * Android Engineer
 */
class PermissionsChecker {

    private val REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 9911

    fun addPermission(
        permissionsList: MutableList<String>,
        permission: String,
        activity: Activity
    ): Boolean {
        if (activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission)
            return activity.shouldShowRequestPermissionRationale(permission)
        }
        return true
    }

    fun checkForCameraWritePermissions(activity: FragmentActivity, workFinish: OnWorkFinish) {
        val permissionsNeeded: MutableList<String> = ArrayList()
        val permissionsList: MutableList<String> = ArrayList()

        if (!addPermission(
                permissionsList,
                Manifest.permission.CAMERA,
                activity
            )
        ) permissionsNeeded.add("CAMERA")
        if (!addPermission(
                permissionsList,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                activity
            )
        ) permissionsNeeded.add("WRITE_EXTERNAL_STORAGE")
        if (permissionsList.size > 0) {
            activity.requestPermissions(
                permissionsList.toTypedArray(),
                REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS
            )
        } else {
            workFinish.onFinish(true)
        }
    }


    fun checkForCameraWritePermissions(fragment: Fragment, workFinish: OnWorkFinish) {
        val permissionsNeeded: MutableList<String> = ArrayList()
        val permissionsList: MutableList<String> = ArrayList()

        fragment.activity?.let {
            if (!addPermission(
                    permissionsList,
                    Manifest.permission.CAMERA,
                    it
                )
            ) permissionsNeeded.add("CAMERA")

            if (!addPermission(
                    permissionsList,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    it
                )
            ) permissionsNeeded.add("WRITE_EXTERNAL_STORAGE")

            if (permissionsList.size > 0) {
                fragment.requestPermissions(
                    permissionsList.toTypedArray(),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS
                )
            }
        }

        workFinish.onFinish(true)
    }

}