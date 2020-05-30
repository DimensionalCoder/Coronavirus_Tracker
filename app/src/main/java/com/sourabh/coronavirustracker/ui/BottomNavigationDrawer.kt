package com.sourabh.coronavirustracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sourabh.coronavirustracker.R
import com.sourabh.coronavirustracker.databinding.FragmentBottomNavigationDrawerBinding

class BottomNavigationDrawer : BottomSheetDialogFragment() {

    companion object {
        fun getInstance() = BottomNavigationDrawer()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentBottomNavigationDrawerBinding.inflate(inflater)

        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(binding.navigation, navController)


        /**
         * Prevent reloading the same fragment
         */
        binding.navigation.setNavigationItemSelectedListener {

            // If current fragment is not the destination then navigate
            if (it.itemId != navController.currentDestination!!.id) {

                // Attempt to navigate to the [NavDestination] associated with this [MenuItem]
                it.onNavDestinationSelected(findNavController())
            }

            dismiss() // Close drawer after navigating
            true
        }

        return binding.root
    }
}