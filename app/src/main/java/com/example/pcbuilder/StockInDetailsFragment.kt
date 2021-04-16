package com.example.pcbuilder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pcbuilder.databinding.FragmentLoginBinding
import com.example.pcbuilder.databinding.FragmentStockInDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_stock_in_details.*

class StockInDetailsFragment : Fragment() {
    private var count:Int = 0
    private lateinit var auth : FirebaseAuth
    private var binding: FragmentStockInDetailsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //save inflated view to fragmentBinding
        val fragmentBinding = FragmentStockInDetailsBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        fragmentBinding.btnStockinAddqty.setOnClickListener{ addQuantity() }
        fragmentBinding.btnStockinMinusqty.setOnClickListener{ minusQuantity() }

        //initialize to binding
        binding = fragmentBinding
        //create view with inflated fragmentBinding
        return fragmentBinding.root
    }

    private fun addQuantity(){
        count++
        txt_stockin_quantity.setText(" " + count)
    }

    private fun minusQuantity(){
        if(count > 1){
            count--
            txt_stockin_quantity.setText(" " + count)
        }
    }
}