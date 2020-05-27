package com.sourabh.coronavirustracker.ui.india.adapters

import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.qtalk.recyclerviewfastscroller.RecyclerViewFastScroller
import com.sourabh.coronavirustracker.BR
import com.sourabh.coronavirustracker.R
import com.sourabh.coronavirustracker.databinding.IndianListItemBinding
import com.sourabh.coronavirustracker.databinding.IndianTotalListItemBinding
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

        fun bind(
            item: T,
            onCLickListener: IndianStatesAdapter.OnItemClickListener?
        ) {
            binding.setVariable(BR.data, item)
            val config = binding.root.resources.configuration
            val densityDpi = config.densityDpi
            val orientation = config.orientation

            when (itemViewType) {
                R.layout.indian_total_list_item -> {
                    binding as IndianTotalListItemBinding
                    if (densityDpi > 420 && orientation == Configuration.ORIENTATION_PORTRAIT) {
                        setIndiaBindingTVSize(binding)
                    }
                }
                R.layout.indian_list_item -> {
                    binding as IndianListItemBinding
                    binding.clickListener = onCLickListener
                    val newData = binding.newData
                    item as StatewiseDetails

                    // To hide "new" indicator in the list item
                    if (item.deltaConfirmed != 0 || item.deltaRecovered != 0 || item.deltaDeaths != 0) {
                        newData.visibility = View.VISIBLE
                    } else {
                        newData.visibility = View.GONE
                    }
                }
            }

            binding.executePendingBindings()
        }

        private fun setIndiaBindingTVSize(binding: IndianTotalListItemBinding) {
            val size = 24f
            binding.acitveCasesTv.textSize = size
            binding.recoveredTv.textSize = size
            binding.deathsTv.textSize = size
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

    abstract override fun getFilter(): Filter

    override fun onChange(position: Int): CharSequence {
        val data = getItem(position)
        return when (data) {
            is StatewiseDetails -> data.stateOrUT
            else -> ""
        }
    }


}