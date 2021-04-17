package com.example.pcbuilder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.pcbuilder.databinding.FragmentStockInDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.integration.android.IntentIntegrator
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

    lateinit var btnBarcode: ImageButton
    lateinit var txtBarcode: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnBarcode = view.findViewById(R.id.btn_stockin_scan)
        txtBarcode = view.findViewById(R.id.txt_stockin_code)


        btnBarcode.setOnClickListener {
            val intentIntegrator = IntentIntegrator.forSupportFragment(this)
            intentIntegrator.setBeepEnabled(false)
            intentIntegrator.setCameraId(0)
            intentIntegrator.setPrompt("SCAN")
            intentIntegrator.setBarcodeImageEnabled(false)
            intentIntegrator.initiateScan()
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(context, "cancelled", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("Fragment", "Scanned from Fragment")
                Toast.makeText(context, "Scanned -> " + result.contents, Toast.LENGTH_SHORT)
                    .show()

                txtBarcode.text = result.contents

                Log.d("Fragment", "$result")
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}