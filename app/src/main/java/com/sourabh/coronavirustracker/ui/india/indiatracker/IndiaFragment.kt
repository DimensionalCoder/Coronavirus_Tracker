package com.sourabh.coronavirustracker.ui.india.indiatracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sourabh.coronavirustracker.databinding.FragmentIndiaBinding
import com.sourabh.coronavirustracker.network.RetrofitBuilder
import com.sourabh.coronavirustracker.repository.MainRepository

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
        adapter = IndianStatesAdapter(
            IndianStatesAdapter.OnItemClickListener { statewise ->
                viewModel.listItemClicked(statewise)
            }
        )
    }

    private fun viewModelObservers(viewModel: IndiaViewModel) {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}