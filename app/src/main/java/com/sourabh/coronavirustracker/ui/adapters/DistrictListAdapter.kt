package com.sourabh.coronavirustracker.ui.adapters

import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sourabh.coronavirustracker.R
import com.sourabh.coronavirustracker.databinding.DistrictHeaderBinding
import com.sourabh.coronavirustracker.databinding.DistrictListItemBinding
import com.sourabh.coronavirustracker.model.Districts
import com.sourabh.coronavirustracker.model.StatewiseDetails
import com.sourabh.coronavirustracker.ui.util.setUpSnackbar
import kotlin.math.log10

/**
 * Passing in List<Any> which contains both Statewise data and Districts data, statewise data is used
 * in the header.
 */
class DistrictListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val HEADER = 0
        const val LIST = 1
    }

    private lateinit var listOfData: List<Any>

    fun submitList(list: List<Any>) {
        listOfData = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == HEADER) {
            HeaderViewHolder.from(
                parent
            )
        } else {
            ListItemViewHolder.from(
                parent
            )
        }
    }

    override fun getItemCount(): Int {
        return listOfData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == 0) {
            (holder as HeaderViewHolder).bind(listOfData[0] as StatewiseDetails)
        } else {
            @Suppress("UNCHECKED_CAST")
            (holder as ListItemViewHolder)
                .bind(listOfData[position] as Districts)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            HEADER
        } else {
            LIST
        }
    }

    private class HeaderViewHolder(private val binding: DistrictHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(statewiseDetails: StatewiseDetails) {
            binding.statewiseDetails = statewiseDetails
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DistrictHeaderBinding.inflate(layoutInflater, parent, false)
                return HeaderViewHolder(
                    binding
                )
            }
        }
    }

    private class ListItemViewHolder(private val binding: DistrictListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Districts) {
            binding.districtData = item

            /**
             * Hide the Arrow and increase numbers when no data is available
             */
            binding.confirmedIncrease.visibility = getVisibility(item.delta.confirmed)
            binding.recoveredIncrease.visibility = getVisibility(item.delta.recovered)
            binding.deceasedIncrease.visibility = getVisibility(item.delta.deceased)

            /**
             * To put the new cases below the confirmed cases when the space isn't adequate
             */
            val config = binding.root.resources.configuration
            binding.confirmedBox.orientation =
                getOrientation(item.confirmed, item.delta.confirmed, config)

            binding.recoveredBox.orientation =
                getOrientation(item.recovered, item.delta.recovered, config)

            binding.deathBox.orientation =
                getOrientation(item.deceased, item.delta.deceased, config)

            /**
             * Show district notes if available
             */
            if (!item.notes.isNullOrBlank()) {
                val district = binding.district
                district.setTextColor(ContextCompat.getColor(district.context, R.color.colorWhite))
                binding.ll.setOnClickListener {
                    Snackbar.make(binding.root, item.notes.toString(), Snackbar.LENGTH_LONG)
                        .setUpSnackbar()
                        .show()
                }
            }

            binding.executePendingBindings()
        }


        private fun getVisibility(caseDelta: Int): Int {
            return if (caseDelta == 0) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }

        /**
         * Change orientation when no space is available, undo when orientation is Landscape
         */
        private fun getOrientation(case: Int, deltaCase: Int, config: Configuration): Int {
            val orientation = config.orientation
            val densityDpi = config.densityDpi
            val caseDigits = getDigits(case)
            val deltaDigits = getDigits(deltaCase)

            return if ((caseDigits > 4
                        || caseDigits > 3 && (deltaDigits >= 2 || deltaCase < -9)
                        || caseDigits <= 2 && deltaDigits > 2
                        || (densityDpi >= 500 && (deltaDigits >= 2 || deltaCase < -9))
                        || densityDpi >= 540) && orientation == Configuration.ORIENTATION_PORTRAIT
            ) {
                LinearLayout.VERTICAL
            } else {
                LinearLayout.HORIZONTAL
            }
        }

        private fun getDigits(case: Int): Int {
            return (log10(case.toDouble()) + 1).toInt()
        }

        companion object {
            fun from(parent: ViewGroup): ListItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DistrictListItemBinding.inflate(layoutInflater, parent, false)
                return ListItemViewHolder(
                    binding
                )
            }
        }


    }
}