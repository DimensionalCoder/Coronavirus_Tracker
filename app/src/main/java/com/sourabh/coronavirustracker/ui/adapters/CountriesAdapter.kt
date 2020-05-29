package com.sourabh.coronavirustracker.ui.adapters

import android.widget.Filter
import androidx.recyclerview.widget.DiffUtil
import com.sourabh.coronavirustracker.R
import com.sourabh.coronavirustracker.model.WorldDataModel
import java.util.*

class CountriesAdapter : BaseRecyclerAdapter<WorldDataModel>(DiffItem) {

    // Pass the list from the fragment
    private lateinit var worldDataList: MutableList<WorldDataModel>
    fun setFilterList(worldList: MutableList<WorldDataModel>) {
        worldDataList = worldList
    }

    // To show the filtered list
    private lateinit var worldFilteredList: MutableList<WorldDataModel>

    companion object DiffItem : DiffUtil.ItemCallback<WorldDataModel>() {
        override fun areItemsTheSame(oldItem: WorldDataModel, newItem: WorldDataModel): Boolean {
            return oldItem.country == newItem.country
        }

        override fun areContentsTheSame(oldItem: WorldDataModel, newItem: WorldDataModel): Boolean {
            return oldItem == newItem
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).country == "World") {
            R.layout.world_total_list_item
        } else {
            R.layout.world_list_item
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                worldFilteredList = mutableListOf()
                if (constraint.isNullOrBlank()) {
                    worldFilteredList.addAll(worldDataList)
                } else {
                    val inputString = constraint.toString().toLowerCase(Locale.ENGLISH).trim()
                    for (data in worldDataList) {
                        if (data.country.toLowerCase(Locale.ENGLISH).startsWith(inputString)
                            && data.country != "World"
                        ) {
                            worldFilteredList.add(data)
                        }
                    }
                }

                val filterResult = FilterResults()
                filterResult.values = worldFilteredList
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                @Suppress("UNCHECKED_CAST")
                submitList(results?.values as MutableList<WorldDataModel>)
            }

        }
    }
}
