package com.pietrantuono.podcasts.addpodcast.view

import android.app.Activity
import android.support.v4.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager


fun Activity.hideKeyboard() {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = this.currentFocus
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment.hideKeyboard() {
    val imm = this.activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = this.activity.currentFocus
    if (view == null) {
        view = View(this.activity)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}