package com.example.pcbuilder

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.pcbuilder.databinding.FragmentLoginBinding
import com.example.pcbuilder.databinding.FragmentStockInBinding
import com.google.firebase.auth.FirebaseAuth

class StockInFragment : Fragment() {
    private lateinit var auth : FirebaseAuth
    private var binding: FragmentStockInBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentStockInBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()

        fragmentBinding.btnStockinFloat.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_stockInFragment_to_stockInDetailsFragment)
        }

        //initialize to binding
        binding = fragmentBinding
        //create view with inflated fragmentBinding
        return fragmentBinding.root
    }


}