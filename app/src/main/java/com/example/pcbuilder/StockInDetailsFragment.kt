package com.example.pcbuilder

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.pcbuilder.data.StockIn
import com.example.pcbuilder.databinding.FragmentStockInDetailsBinding
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.fragment_stock_in_details.*
import kotlinx.android.synthetic.main.fragment_stock_in_details.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class StockInDetailsFragment(action: Boolean, id: String?) : Fragment() {
    private val stockinCollectionRef = Firebase.firestore.collection("stockin")
    private val new = action
    private val itemid = id
    private var newId: Int = 0
    @RequiresApi(Build.VERSION_CODES.O)
    private val currentDateTime = LocalDateTime.now()
    @RequiresApi(Build.VERSION_CODES.O)

    private var qty:Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentStockInDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_stock_in_details, container,false)

        binding.btnStockinAddqty.setOnClickListener{ addQuantity() }
        binding.btnStockinMinusqty.setOnClickListener{ minusQuantity() }

//        if(new){
//            setEditable(true, binding)
//            binding.btnItemDetailEdit.text = "Add"
//            itemCollectionRef.orderBy("itemId", Query.Direction.DESCENDING).limit(1)
//            itemCollectionRef.get().addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    for (document in task.result!!) {
//                        binding.txtItemId.text = (document.data["itemId"].toString().toInt() + 1).toString()
//                        newId = document.data["itemId"].toString().toInt() + 1
//                    }
//                }
//            }

        return view
    }

    private fun saveStockIn(stockin: StockIn) = CoroutineScope(Dispatchers.IO).launch {
        try{
            stockinCollectionRef.add(stockin).await()
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

    fun addQuantity(){
        qty++
        txt_stockin_quantity.setText(" " + qty)
    }

    fun minusQuantity(){
        if(qty > 1){
            qty--
            txt_stockin_quantity.setText(" " + qty)
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