package com.sourabh.coronavirustracker.ui.india.indiadetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sourabh.coronavirustracker.databinding.FragmentIndiaDetailsBinding
import com.sourabh.coronavirustracker.ui.india.adapters.DistrictListAdapter

class IndiaDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val binding = FragmentIndiaDetailsBinding.inflate(inflater)

        val arguments = IndiaDetailsFragmentArgs.fromBundle(requireArguments())
        val viewModelFactory =
            IndianDetailsViewModelFactory(arguments.statewiseData, arguments.districtData.toList())
        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(IndianDetailsViewModel::class.java)

        val recyclerView = binding.districtList
        val adapter = DistrictListAdapter()
        viewModel.listOfData.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        adapter.submitList(arguments.districtData.toList())
        recyclerView.adapter = adapter

        return binding.root
    }

}
