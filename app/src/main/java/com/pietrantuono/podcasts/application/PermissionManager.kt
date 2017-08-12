package com.pietrantuono.podcasts.application

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import javax.inject.Inject

class PermissionManager @Inject constructor(private val context: Context) {

    val writeExternalPermissionGranted: Boolean
        get() =
        PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)


}