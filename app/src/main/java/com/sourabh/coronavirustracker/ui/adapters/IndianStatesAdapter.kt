@file:Suppress("SpellCheckingInspection")

package com.sourabh.coronavirustracker.ui.adapters

import android.util.Log
import android.widget.Filter
import androidx.recyclerview.widget.DiffUtil
import com.sourabh.coronavirustracker.R
import com.sourabh.coronavirustracker.model.StatewiseDetails
import java.util.*


class IndianStatesAdapter(
    onCLickListener: OnItemClickListener
) :
    BaseRecyclerAdapter<StatewiseDetails>(
        DiffCallBack, onCLickListener
    ) {

    // Pass the list from the fragment
    var stateWiseDetailsList: MutableList<StatewiseDetails> = mutableListOf()

    // Used to show the filtered list
    private lateinit var statewiseFilterList: MutableList<StatewiseDetails>


    companion object DiffCallBack : DiffUtil.ItemCallback<StatewiseDetails>() {
        override fun areItemsTheSame(
            oldItem: StatewiseDetails,
            newItem: StatewiseDetails
        ): Boolean {
            return oldItem.stateOrUT == newItem.stateOrUT
        }

        override fun areContentsTheSame(
            oldItem: StatewiseDetails,
            newItem: StatewiseDetails
        ): Boolean {
            return oldItem == newItem
        }
    }

    /**
     * Set the layouts here
     */
    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).stateOrUT == "Total") {
            R.layout.indian_total_list_item
        } else {
            R.layout.india_list_item
        }
    }

    /**
     * Click Listener
     */
    class OnItemClickListener(private val clickListener: (statewise: StatewiseDetails) -> Unit) {
        fun onClick(statewise: StatewiseDetails) {
            Log.i("RVClickListener", "Item clicked: ${statewise.stateOrUT}")
            clickListener(statewise)
        }
    }


    /**
     * SearchView Filter
     */
    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                statewiseFilterList = mutableListOf()

                if (constraint.isNullOrBlank()) {
                    statewiseFilterList.addAll(stateWiseDetailsList) // Show whole statewise list
                } else {
                    val input = constraint.toString().toLowerCase(Locale.ENGLISH).trim()
                    for (data in stateWiseDetailsList) {
                        if (data.stateOrUT.toLowerCase(Locale.ENGLISH).startsWith(input)
                            && data.stateOrUT != "Total"
                        ) {
                            statewiseFilterList.add(data)
                        }
                    }
                }

                val filterResult = FilterResults()
                filterResult.values = statewiseFilterList
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                @Suppress("UNCHECKED_CAST")
                submitList(results?.values as MutableList<StatewiseDetails>)
            }

        }
    }
}