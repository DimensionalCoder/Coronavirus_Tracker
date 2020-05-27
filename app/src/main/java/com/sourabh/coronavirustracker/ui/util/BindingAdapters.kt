package com.sourabh.coronavirustracker.ui.util

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat

// Using SimpleDateFormat since Java 8 date time classes require min api 26
@SuppressLint("SimpleDateFormat")
@BindingAdapter("updateTime")
fun TextView.setUpdateTime(updateTime: String?) {
    updateTime?.let {
        val dateTime = updateTime.split(" ")
        val time = dateTime[1]

        val tf = SimpleDateFormat("HH:mm:ss")
        val twelve = SimpleDateFormat("hh:mm a")
        val formattedTime = twelve.format(tf.parse(time)!!)

        val date = dateTime[0]
        val format = SimpleDateFormat("dd/mm/yyyy")
        val stringDate = SimpleDateFormat("dd MMM yyyy")
        val formattedDate = stringDate.format(format.parse((date))!!)

        val dateTimeText = "$formattedTime, $formattedDate"
        text = dateTimeText
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
                    .show()
//                    .setupSnackBar()
            }
        } else {
            this.visibility = View.GONE
        }
    }
}