package com.example.pcbuilder.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.example.pcbuilder.R
import com.example.pcbuilder.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
//    private val sharedViewModel : ApplicationTaskViewModel by activityViewModels()

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val fragmentBinding = FragmentHomeBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            //viewModel = sharedViewModel bind in xml
            homeFragment = this@HomeFragment //bind fragment in xml
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    fun navigate(choice : Int){
        when(choice){
            1 -> {
                findNavController().navigate(R.id.action_nav_home_to_stockInFragment)
            }
            2 -> {
                //room record
            }
            3 -> {
                findNavController().navigate(R.id.action_nav_home_to_itemListFragment)
            }
            4 -> {
                findNavController().navigate(R.id.action_nav_home_to_warehouseFragment)
            }
            5 -> {
                findNavController().navigate(R.id.action_nav_home_to_userProfileFragment)
            }
            6 -> {
                //housekeeping
            }
        }
    }
}