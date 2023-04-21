package com.base.hilt.extensions

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Bitmap.createBitmap
import android.graphics.Canvas
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.android.material.snackbar.Snackbar
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created By Richa Shah on 9/08/2022 at 2:00 PM
 * Extension Methods with usage of #View
 */
/**
 * Shows the Snackbar inside an Activity or Fragment
 *
 * @param messageRes Text to be shown inside the Snackbar
 * @param length Duration of the Snackbar
 * @param f Action of the Snackbar
 */
fun View.showSnackbar(@StringRes messageRes: Int, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit) {
    val snackBar = Snackbar.make(this, resources.getString(messageRes), length)
    snackBar.f()
    snackBar.show()
}

