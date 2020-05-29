package com.sourabh.coronavirustracker.ui.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@BindingAdapter("updateTime")
fun TextView.setUpdateTime(updateTime: String?) {
    updateTime?.let {
        val dateTime = updateTime.split(" ")

        text = LocalDateTime.parse(
            "$dateTime[1] $dateTime[0",
            DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy")
        ).format(DateTimeFormatter.ofPattern("hh:mm a, dd MMM yyyy"))
    }
}

@BindingAdapter("stateNotes")
fun ImageView.setStateNotes(stateNotes: String?) {
    stateNotes?.let {
        val regex = Regex("<br[\\s/]*>")
        val s = regex.replace(stateNotes, "")
        if (s.isNotBlank()) {
            this.setOnClickListener {
                Snackbar.make(this, s, Snackbar.LENGTH_LONG)
                    .setUpSnackbar()
                    .show()
            }
        } else {
            this.visibility = View.GONE
        }
    }
}