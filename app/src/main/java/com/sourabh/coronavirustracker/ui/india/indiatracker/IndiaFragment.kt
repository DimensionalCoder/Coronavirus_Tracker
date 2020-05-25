package com.sourabh.coronavirustracker.ui.india.indiatracker

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sourabh.coronavirustracker.R
import com.sourabh.coronavirustracker.databinding.FragmentIndiaBinding
import com.sourabh.coronavirustracker.network.Resource
import com.sourabh.coronavirustracker.network.RetrofitBuilder
import com.sourabh.coronavirustracker.repository.MainRepository
import com.sourabh.coronavirustracker.ui.india.adapters.IndianStatesAdapter

class IndiaFragment : Fragment() {

    private var _binding: FragmentIndiaBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: IndianStatesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentIndiaBinding.inflate(inflater)
        setHasOptionsMenu(true)

        val indianDataService = RetrofitBuilder.indianDataService
        val mainRepository = MainRepository(indianDataService)
        val viewModelFactory = IndiaViewModelFactory(mainRepository)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(IndiaViewModel::class.java)

        setUpRecyclerView(viewModel)
        viewModelObservers(viewModel)

        return binding.root
    }

    private fun setUpRecyclerView(viewModel: IndiaViewModel) {
        val recyclerView = binding.indianRv

        /**
         * onClick() in the xml passes the statewise data to the OnItemClickListener class
         * statewise data is passed into the viewModel every time the list item is clicked
         */
        adapter =
            IndianStatesAdapter(
                IndianStatesAdapter.OnItemClickListener { statewise ->
                    viewModel.listItemClicked(statewise)
                }
            )

        /**
         *  To handle recyclerview position when orientation changes
         */
        adapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        //  Prevents multiple clicks in the recyclerView
        recyclerView.isMotionEventSplittingEnabled = false
        recyclerView.setHasFixedSize(true)

        // Prevent over scroll animation
        recyclerView.overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        recyclerView.adapter = adapter
    }

    private fun viewModelObservers(viewModel: IndiaViewModel) {

        viewModel.indianStatewiseDetails.observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it) {
                    is Resource.LOADING -> Log.i("IndiaFragment", "Loading")
                    is Resource.SUCCESS -> {
                        adapter.submitList(it.data)
                        adapter.stateWiseDetailsList = it.data as MutableList
                        Log.i("IndiaFragment", "Success ${it.data}")
                    }
                    is Resource.FAILURE -> {
                        Log.i("IndiaFragment", "Indian Data FAILED ${it.e}")
                        adapter.submitList(ArrayList())
                    }
                }
            }
        })

        viewModel.navigateToIndianFragment.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(
                    IndiaFragmentDirections.actionIndiaFragmentToIndiaDetailsFragment(
                        it.first,
                        it.second.toTypedArray()
                    )
                )
                viewModel.navigationComplete()
            }
        })
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
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    adapter.filter.filter(newText)
                    return false
                }
            })
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.indianRv.adapter = null
        _binding = null
    }
}