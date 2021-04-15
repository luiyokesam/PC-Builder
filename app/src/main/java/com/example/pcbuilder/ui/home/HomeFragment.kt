package com.example.pcbuilder.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
            dashboardFragment = this@HomeFragment //bind fragment in xml
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    fun navigate(choice : Int){
        when(choice){
            1 -> {
                //if else
//                findNavController().navigate(R.id.action_dashBoardFragment_to_managerProfileFragment)
                //findNavController().navigate(R.id.action_dashBoardFragment_to_staffProfileFragment)
            }
            2 -> {
                //room record
            }
            3 -> {
//                findNavController().navigate(R.id.action_dashBoardFragment_to_reservationListFragment)
            }
            4 -> {
                //clock in and out
            }
            5 -> {
                //payment
            }
            6 -> {
                //housekeeping
            }
            7 -> {
                //check in
            }
            8 -> {
                //staff
            }
        }
    }
}