package com.sourabh.coronavirustracker.ui.adapters

import android.content.res.Configuration
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.qtalk.recyclerviewfastscroller.RecyclerViewFastScroller
import com.sourabh.coronavirustracker.BR
import com.sourabh.coronavirustracker.R
import com.sourabh.coronavirustracker.databinding.IndianTotalListItemBinding
import com.sourabh.coronavirustracker.databinding.MainListItemBinding
import com.sourabh.coronavirustracker.model.StatewiseDetails

abstract class BaseRecyclerAdapter<T>(
    diffCallback: DiffUtil.ItemCallback<T>,
    private val onCLickListener: IndianStatesAdapter.OnItemClickListener
) :
    ListAdapter<T, BaseRecyclerAdapter.DataBindingViewHolder<T>>(diffCallback), Filterable,
    RecyclerViewFastScroller.OnPopupTextUpdate {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<T> {
        return DataBindingViewHolder.from(
            parent,
            viewType
        )
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<T>, position: Int) {
        holder.bind(getItem(position), onCLickListener)
    }

    class DataBindingViewHolder<T>(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: T, onCLickListener: IndianStatesAdapter.OnItemClickListener) {

            binding.setVariable(BR.data, item)
            val config = binding.root.resources.configuration
            val densityDpi = config.densityDpi
            val orientation = config.orientation

            when (itemViewType) {
                R.layout.indian_total_list_item -> {
                    binding as IndianTotalListItemBinding
                    if (densityDpi > 420 && orientation == Configuration.ORIENTATION_PORTRAIT) {
                        Log.i("BaseRecyclerView", "$densityDpi")
                        setIndiaBindingTVSize(binding)
                    }
                }
                R.layout.main_list_item -> {
                    binding as MainListItemBinding
                    binding.clickListener = onCLickListener
                    hideNewIndicator(binding.newData, item as StatewiseDetails)
                    if (densityDpi > 500 && orientation == Configuration.ORIENTATION_PORTRAIT) {
                        setIndiaListItemTextSize(binding)
                    }
                }
            }

            binding.executePendingBindings()
        }

        /**
         * To set the textSize when display density changes
         * AutoSizeText is a bad solution since the textView sizes don't change equally
         */
        private fun setIndiaBindingTVSize(binding: IndianTotalListItemBinding) {
            val size = 24f
            binding.acitveCasesTv.textSize = size
            binding.recoveredTv.textSize = size
            binding.deathsTv.textSize = size
        }

        private fun setIndiaListItemTextSize(binding: MainListItemBinding) {
            val size = 12f
            binding.conf.textSize = size
            binding.rec.textSize = size
            binding.dea.textSize = size
        }

        /**
         * To hide "new" indicator in the list item when no new data is available
         */
        private fun hideNewIndicator(newTV: TextView, item: StatewiseDetails) {
            if (item.deltaConfirmed != 0 || item.deltaRecovered != 0 || item.deltaDeaths != 0) {
                newTV.visibility = View.VISIBLE
            } else {
                newTV.visibility = View.GONE
            }
        }

        companion object {
            fun <T> from(parent: ViewGroup, viewType: Int): DataBindingViewHolder<T> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DataBindingUtil.inflate<ViewDataBinding>(
                    layoutInflater,
                    viewType,
                    parent,
                    false
                )
                return DataBindingViewHolder(
                    binding
                )
            }
        }
    }

    /**
     * Filter for searchView
     */
    abstract override fun getFilter(): Filter

    /**
     * To display the name on FastRecyclerView Scroller
     */
    override fun onChange(position: Int): CharSequence {
        val data = getItem(position)
        return when (data) {
            is StatewiseDetails -> data.stateOrUT
            else -> ""
        }
    }


}