package com.sourabh.coronavirustracker.ui.india.indiadetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sourabh.coronavirustracker.databinding.FragmentIndiaDetailsBinding

/**
 * A simple [Fragment] subclass.
 */
class IndiaDetailsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentIndiaDetailsBinding.inflate(inflater)

        val arguments = IndiaDetailsFragmentArgs.fromBundle(requireArguments())

        Toast.makeText(requireContext(), "State is ${arguments.districtData[0].district}", Toast.LENGTH_SHORT).show()
        return binding.root
    }

}
