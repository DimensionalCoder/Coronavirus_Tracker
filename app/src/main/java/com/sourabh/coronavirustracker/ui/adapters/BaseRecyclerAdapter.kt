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
import com.sourabh.coronavirustracker.R
import com.sourabh.coronavirustracker.databinding.IndiaListItemBinding
import com.sourabh.coronavirustracker.databinding.IndianTotalListItemBinding
import com.sourabh.coronavirustracker.databinding.WorldListItemBinding
import com.sourabh.coronavirustracker.databinding.WorldTotalListItemBinding
import com.sourabh.coronavirustracker.model.StatewiseDetails
import com.sourabh.coronavirustracker.model.WorldDataModel

abstract class BaseRecyclerAdapter<T>(
    diffCallback: DiffUtil.ItemCallback<T>,
    private val onCLickListener: IndianStatesAdapter.OnItemClickListener? = null
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
        if (onCLickListener != null) {
            holder.bind(getItem(position), onCLickListener)
        } else {
            holder.bind(getItem(position))
        }
    }

    class DataBindingViewHolder<T>(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: T, onCLickListener: IndianStatesAdapter.OnItemClickListener? = null) {

            val config = binding.root.resources.configuration
            val densityDpi = config.densityDpi
            val orientation = config.orientation

            when (itemViewType) {
                R.layout.indian_total_list_item -> {
                    binding as IndianTotalListItemBinding
                    binding.data = item as StatewiseDetails
                    Log.i("BaseRecyclerView", "$densityDpi")
                    setIndiaBindingTVSize(binding, densityDpi, orientation)

                }
                R.layout.india_list_item -> {
                    binding as IndiaListItemBinding
                    binding.data = item as StatewiseDetails
                    onCLickListener?.let { binding.clickListener = onCLickListener }
                    hideNewIndicator(binding.newData, item as StatewiseDetails)
                    setMainListItemTextSize(
                        binding.conf, binding.dea, binding.rec, densityDpi, orientation
                    )
                }
                R.layout.world_total_list_item -> {
                    binding as WorldTotalListItemBinding
                    binding.data = item as WorldDataModel
                    setWorldBindingTVSize(binding, densityDpi, orientation)
                }
                R.layout.world_list_item -> {
                    binding as WorldListItemBinding
                    binding.data = item as WorldDataModel
                    setMainListItemTextSize(
                        binding.conf, binding.dea, binding.rec, densityDpi, orientation
                    )
                }
            }

            binding.executePendingBindings()
        }

        /**
         * To set the textSize when display density changes
         * AutoSizeText is a bad solution since the textView sizes don't change equally
         */
        private fun setIndiaBindingTVSize(
            binding: IndianTotalListItemBinding, densityDpi: Int, orientation: Int
        ) {
            if (densityDpi in 421..460 && orientation == Configuration.ORIENTATION_PORTRAIT) {
                val size = 28f
                tvSize(binding, size)
            } else if (densityDpi > 460 && orientation == Configuration.ORIENTATION_PORTRAIT) {
                val size = 24f
                tvSize(binding, size)
            }
        }

        private fun setWorldBindingTVSize(
            binding: WorldTotalListItemBinding, densityDpi: Int, orientation: Int
        ) {
            if (densityDpi > 460 && orientation == Configuration.ORIENTATION_PORTRAIT) {
                val size = 24f
                binding.recoveredTv.textSize = size
                binding.deathsTv.textSize = size
            }
        }

        private fun setMainListItemTextSize(
            conf: TextView, dea: TextView, rec: TextView, densityDpi: Int, orientation: Int
        ) {
            if (densityDpi > 500 && orientation == Configuration.ORIENTATION_PORTRAIT) {
                val size = 12f
                conf.textSize = size
                dea.textSize = size
                rec.textSize = size
            }
        }

        private fun tvSize(binding: IndianTotalListItemBinding, size: Float) {
            binding.acitveCasesTv.textSize = size
            binding.recoveredTv.textSize = size
            binding.deathsTv.textSize = size
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
        return when (val data = getItem(position)) {
            is StatewiseDetails -> data.stateOrUT
            is WorldDataModel -> data.country
            else -> ""
        }
    }


}