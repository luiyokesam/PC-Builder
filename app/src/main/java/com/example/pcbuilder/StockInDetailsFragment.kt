package com.example.pcbuilder

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pcbuilder.data.Product
import com.example.pcbuilder.data.StockIn
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.android.synthetic.main.fragment_stock_in_details.*
import kotlinx.android.synthetic.main.fragment_stock_in_details.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.StringBuilder

class StockInDetailsFragment : Fragment() {
    private var count:Int = 0
    private val stockinCollectionRef = Firebase.firestore.collection("stockin")
    private val productCollectionRef = Firebase.firestore.collection("products")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_stock_in_details, container, false)

        view.btn_stockin_addqty.setOnClickListener{ addQuantity() }
        view.btn_stockin_minusqty.setOnClickListener{ minusQuantity() }

        view.btn_stockin_add.setOnClickListener {
            val inbarcode = txt_stockin_code.text.toString()
            val inquantity = txt_stockin_quantity.text.toString().toInt()
            val indate = txt_stockin_date.text.toString()
            val stockin = StockIn(inbarcode, inquantity, indate)
            saveStockIn(stockin)
        }

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
               /* productCollectionRef.get().addOnCompleteListener{
                        task -> if (task.isSuccessful){
                    for(document in task.result!!){
                        if(document.data["productCode"].toString() == txtBarcode.text){
                            txt_stockin_cname.setText(document.data["productCompany"].toString())
                            txt_stockin_pname.setText(document.data["productName"].toString())
                            txt_stockin_ptype.setText(document.data["productType"].toString())
                            txt_stockin_pprice.setText(document.data["productPrice"].toString())
                        }
                    }
                }
                }*/




                Log.d("Fragment", "$result")
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}