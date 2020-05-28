package com.sourabh.coronavirustracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sourabh.coronavirustracker.R
import com.sourabh.coronavirustracker.databinding.FragmentBottomNavigationDrawerBinding

class BottomNavigationDrawer : BottomSheetDialogFragment() {

    companion object {
        fun getInstance() = BottomNavigationDrawer()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentBottomNavigationDrawerBinding.inflate(inflater)

        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(binding.navigation, navController)

        return binding.root
    }


}