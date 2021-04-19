package com.example.pcbuilder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.pcbuilder.data.Product
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.fragment_item_list_add.*
import kotlinx.android.synthetic.main.fragment_item_list_add.view.*
import kotlinx.android.synthetic.main.fragment_stock_in_details.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception


class ItemListAddFragment : Fragment() {

    private val productCollectionRef = Firebase.firestore.collection("products")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_item_list_add, container, false)

        view.btn_addproduct_add.setOnClickListener {
            val pCode = txt_addproduct_barcode.text.toString()
            val pName = txt_addproduct_pname.text.toString()
            val pCompany = txt_addproduct_cname.text.toString()
            val pType = txt_addproduct_ptype.text.toString()
            val pPrice = txt_addproduct_pprice.text.toString()
            val product = Product(pCode, pName, pCompany, pType, pPrice)
            saveProduct(product)
        }

        return view
    }

    private fun saveProduct(product: Product) = CoroutineScope(Dispatchers.IO).launch {
        try{
            productCollectionRef.add(product).await()
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

    lateinit var btnNewBarcode: ImageButton
    lateinit var txtProduceCode: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnNewBarcode = view.findViewById(R.id.btn_NewProduct_scan)
        txtProduceCode = view.findViewById(R.id.txt_addproduct_barcode)


        btn_NewProduct_scan.setOnClickListener {
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

                txtProduceCode.text = result.contents

                Log.d("Fragment", "$result")
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}