@file:Suppress("SpellCheckingInspection")

package com.sourabh.coronavirustracker.ui.india.indiatracker

import androidx.recyclerview.widget.DiffUtil
import com.sourabh.coronavirustracker.R
import com.sourabh.coronavirustracker.model.StatewiseDetails
import com.sourabh.coronavirustracker.ui.india.BaseRecyclerAdapter


class IndianStatesAdapter(onCLickListener: OnItemClickListener) :
    BaseRecyclerAdapter<StatewiseDetails>(DiffCallBack, onCLickListener) {

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

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            R.layout.indian_total_list_item
        } else {
            R.layout.indian_list_item
        }
    }

    class OnItemClickListener(private val clickListener: (statewise: StatewiseDetails) -> Unit) {
        fun onClick(statewise: StatewiseDetails) {
            clickListener(statewise)
        }
    }
}