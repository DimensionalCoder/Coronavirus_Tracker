package com.sourabh.coronavirustracker.ui.util

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// Using SimpleDateFormat since Java 8 date time classes require min api 26
@SuppressLint("SimpleDateFormat")
@BindingAdapter("updateTime")
fun TextView.setUpdateTime(updateTime: String?) {
    updateTime?.let {
        val dateTime = updateTime.split(" ")
        val time = dateTime[1]
        val date = dateTime[0]

        text = LocalDateTime.parse(
            "$time $date",
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