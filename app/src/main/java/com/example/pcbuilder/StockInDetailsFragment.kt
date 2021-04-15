package com.example.pcbuilder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_stock_in_details.*

class StockInDetailsFragment : Fragment() {
    private var count:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btn_stockin_addqty.setOnClickListener { addQuantity() }
        btn_stockin_minusqty.setOnClickListener { minusQuantity() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stock_in_details, container, false)
    }

    private fun addQuantity(){
        count++
        txt_stockin_quantity.setText(" " + count)
    }

    private fun minusQuantity(){
        count--
        txt_stockin_quantity.setText(" " + count)
    }
}