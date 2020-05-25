package com.sourabh.coronavirustracker.ui.india.adapters

import android.view.LayoutInflater
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
            if (itemViewType == R.layout.indian_list_item) {
                binding.setVariable(BR.clickListener, onCLickListener)
            }
            binding.executePendingBindings()
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