package com.example.pcbuilder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.pcbuilder.data.StockIn
import com.example.pcbuilder.data.Warehouse
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_stock_in_details.*
import kotlinx.android.synthetic.main.fragment_stock_in_details.view.*
import kotlinx.android.synthetic.main.fragment_stock_transfer.*
import kotlinx.android.synthetic.main.fragment_stock_transfer.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class StockTransferFragment : Fragment() {
    private val warehouseCollectionRef = Firebase.firestore.collection("warehouse")
//    private val stockinCollectionRef = Firebase.firestore.collection("stockin")
//    private val productCollectionRef = Firebase.firestore.collection("products")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_stock_transfer, container, false)

        view.btn_stockin_transfer_transfer.setOnClickListener {
            val transrackid = txt_stockin_transfer_rackid.text.toString()
            val transbarcode = txt_stockin_transfer_barcode.text.toString()
            val transquantity = txt_stockin_transfer_quantity.text.toString().toInt()
            val transindate = txt_stockin_date.text.toString()
            val warehouse = Warehouse(transrackid, transbarcode, transquantity, transindate)
            saveStockTransfer(warehouse)
        }

        return view
    }

    private fun saveStockTransfer(warehouse: Warehouse) = CoroutineScope(Dispatchers.IO).launch {
        try{
            warehouseCollectionRef.add(warehouse).await()
            withContext(Dispatchers.Main) {
                Toast.makeText(activity, "Successfully save data", Toast.LENGTH_SHORT).show()
                activity?.onBackPressed()
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(activity, "Please fill out all fields.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}