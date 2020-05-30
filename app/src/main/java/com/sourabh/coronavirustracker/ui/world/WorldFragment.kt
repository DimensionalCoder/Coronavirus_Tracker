package com.sourabh.coronavirustracker.ui.world

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.sourabh.coronavirustracker.R
import com.sourabh.coronavirustracker.databinding.FragmentWorldBinding
import com.sourabh.coronavirustracker.network.Resource
import com.sourabh.coronavirustracker.network.WorldDataService
import com.sourabh.coronavirustracker.repository.MainRepository
import com.sourabh.coronavirustracker.ui.adapters.CountriesAdapter

class WorldFragment : Fragment() {

    private var _binding: FragmentWorldBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CountriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWorldBinding.inflate(inflater)

        val worldDataService = WorldDataService
        val mainRepository = MainRepository(worldDataService = worldDataService)
        val viewModelFactory = WorldViewModelFactory(mainRepository)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(WorldViewModel::class.java)

        val recyclerView = binding.worldRv
        adapter = CountriesAdapter()
        viewModelObserver(viewModel)

        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        // Prevent over scroll animation
        recyclerView.overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        return binding.root
    }

    private fun viewModelObserver(viewModel: WorldViewModel) {

        viewModel.worldData.observe(viewLifecycleOwner, Observer {

            val shimmerScreen = binding.shimmerLoading
            val recyclerView = binding.worldRv
            val retryScreen = binding.retry
            it?.let {
                when (it) {
                    is Resource.LOADING -> {
                        setHasOptionsMenu(false)
                        shimmerScreen.visibility = View.VISIBLE
                        shimmerScreen.startShimmer()
                        recyclerView.visibility = View.GONE
                        retryScreen.visibility = View.GONE
                    }
                    is Resource.SUCCESS -> {
                        adapter.submitList(it.data)
                        adapter.setFilterList(it.data as MutableList)
                        shimmerScreen.stopShimmer()
                        shimmerScreen.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        setHasOptionsMenu(true)
                    }
                    is Resource.FAILURE -> {
                        setHasOptionsMenu(false)
                        shimmerScreen.visibility = View.GONE
                        recyclerView.visibility = View.GONE
                        retryScreen.visibility = View.VISIBLE
                        enableRetry(viewModel)
                    }
                }
            }
        })
    }

    private fun enableRetry(viewModel: WorldViewModel) {
        val retryButton = binding.retryButton
        retryButton.setOnClickListener {
            viewModel.retry()
            binding.shimmerLoading.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.worldRv.adapter = null
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.bottom_bar_menu, menu)

        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.apply {
            imeOptions = EditorInfo.IME_ACTION_DONE
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    adapter.filter.filter(query)
                    onActionViewCollapsed()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    adapter.filter.filter(newText)
                    return true
                }
            })
        }
    }
}
