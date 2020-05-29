package com.sourabh.coronavirustracker.ui.util

import android.view.Gravity
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.google.android.material.snackbar.Snackbar
import com.sourabh.coronavirustracker.R

fun Snackbar.setUpSnackbar(): Snackbar {
    val params = view.layoutParams as CoordinatorLayout.LayoutParams

    params.anchorId = R.id.bar
    params.anchorGravity = Gravity.TOP
    params.gravity = Gravity.TOP

    this.view.layoutParams = params

    // Adding close button
    setAction("Close") {
        // Do nothing, it'll close on click
    }

    setActionTextColor(ContextCompat.getColor(context, R.color.colorWhite))
    view.setBackgroundColor(ContextCompat.getColor(context, R.color.orange))

    view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        .isSingleLine = false

    ViewCompat.setElevation(this.view, 6f)
    return this
}