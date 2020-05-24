package com.sourabh.coronavirustracker.ui.india.indiatracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sourabh.coronavirustracker.databinding.FragmentIndiaBinding

class IndiaFragment : Fragment() {

    private var _binding: FragmentIndiaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentIndiaBinding.inflate(inflater)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}