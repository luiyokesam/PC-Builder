package com.example.pcbuilder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pcbuilder.data.StockIn
import com.example.pcbuilder.data.Warehouse
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.fragment_item_list_add.*
import kotlinx.android.synthetic.main.fragment_stock_transfer.*
import kotlinx.android.synthetic.main.fragment_stock_transfer.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class StockTransferFragment : Fragment() {
    private val stockinCollectionRef = Firebase.firestore.collection("stockin")
    private val warehouseCollectionRef = Firebase.firestore.collection("warehouse")
    private val args by navArgs<StockTransferFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_stock_transfer, container, false)

        view.btn_stockin_transfer_transfer.setOnClickListener {
            val transrackid = txt_stockin_transfer_rackid.text.toString()
            val transbarcode = txt_stockin_transfer_barcode.text.toString()
            val transindate = txt_stockin_transfer_date.text.toString()
            val transquantity = txt_stockin_transfer_quantity.text.toString()
            val warehouse = Warehouse(transrackid, transbarcode, transquantity, transindate)
            saveStockTransfer(warehouse)

            val stockin = getOldStockIn()
            deleteStockIn(stockin)
            findNavController().navigate(R.id.action_stockTransferFragment_to_stockInFragment)
//            activity?.onBackPressed()
        }

        view.txt_stockin_transfer_barcode.setText(args.currentStockIn.productCode)
        view.txt_stockin_transfer_date.setText(args.currentStockIn.inDate)
        view.txt_stockin_transfer_quantity.setText(args.currentStockIn.inQuantity)

        return view
    }

    private fun getOldStockIn(): StockIn {
        val pcode = txt_stockin_transfer_barcode.text.toString()

        return StockIn(pcode)
    }

    private fun deleteStockIn(stockin: StockIn) = CoroutineScope(Dispatchers.IO).launch {
        val productQuery = stockinCollectionRef
            .whereEqualTo("productCode", stockin.productCode)
            .get()
            .await()

        if (productQuery.documents.isNotEmpty()){
            for(document in productQuery) {
                try{
                    stockinCollectionRef.document(document.id).delete().await()
                    Toast.makeText(activity, "Data delete failed", Toast.LENGTH_SHORT).show()
                    /*personCollectionRef.document(document.id).update(mapOf(
                        "firstName" to FieldValue.delete()
                    ))*/
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(activity, "Data deleted", Toast.LENGTH_SHORT).show()
//                        activity?.onBackPressed()
                    }
                }
            }
        }
        else {
            withContext(Dispatchers.Main){
                Toast.makeText(activity, "No data found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveStockTransfer(warehouse: Warehouse) = CoroutineScope(Dispatchers.IO).launch {
        try{
            warehouseCollectionRef.add(warehouse).await()
            withContext(Dispatchers.Main) {
                Toast.makeText(activity, "Successfully save data", Toast.LENGTH_SHORT).show()
//                findNavController().navigate(R.id.action_stockTransferFragment_to_stockInFragment)
//                activity?.onBackPressed()
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(activity, "Please fill out all fields.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    lateinit var btnStockTransferBarcode: ImageButton
    lateinit var txtStockTransferRackId: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnStockTransferBarcode = view.findViewById(R.id.btn_stockin_transfer_scan)
        txtStockTransferRackId = view.findViewById(R.id.txt_stockin_transfer_rackid)


        btnStockTransferBarcode.setOnClickListener {
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

                txtStockTransferRackId.text = result.contents

                Log.d("Fragment", "$result")
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}