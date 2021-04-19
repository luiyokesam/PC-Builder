package com.example.pcbuilder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.pcbuilder.data.Product
import com.example.pcbuilder.data.Warehouse
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.fragment_item_list_add.*
import kotlinx.android.synthetic.main.fragment_item_list_update.*
import kotlinx.android.synthetic.main.fragment_item_list_update.txt_updateproduct_barcode
import kotlinx.android.synthetic.main.fragment_item_list_update.view.*
import kotlinx.android.synthetic.main.fragment_stock_in_details.view.*
import kotlinx.android.synthetic.main.fragment_warehouse_rack_transfer.*
import kotlinx.android.synthetic.main.fragment_warehouse_rack_transfer.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class WarehouseRackTransferFragment : Fragment() {
    private val warehouseCollectionRef = Firebase.firestore.collection("warehouse")
    private val args by navArgs<WarehouseRackTransferFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_warehouse_rack_transfer, container, false)

        view.btn_warehouse_rack_transfer_transfer.setOnClickListener {
            val oldrack = getOldRack()
            val newrackmap = getNewRackMap()
            updateRack(oldrack, newrackmap)
        }

        view.txt_warehouse_rack_transfer_rackid.setText(args.currentRack.rackid)
        view.txt_warehouse_rack_transfer_barcode.setText(args.currentRack.productCode)
        view.txt_warehouse_rack_transfer_date.setText(args.currentRack.inQuantity)
        view.txt_warehouse_rack_transfer_quantity.setText(args.currentRack.inDate)

        setHasOptionsMenu(true)
        return view
    }

    private fun getOldRack(): Warehouse {
        val rackid = txt_warehouse_rack_transfer_rackid.text.toString()
        val pcode = txt_warehouse_rack_transfer_barcode.text.toString()

        return Warehouse(rackid, pcode)
    }

    private fun getNewRackMap(): Map<String, Any> {
        val rackid = txt_warehouse_rack_transfer_rackid.text.toString()
        val pcode = txt_warehouse_rack_transfer_barcode.text.toString()
        val rackdate = txt_warehouse_rack_transfer_date.text.toString()
        val rackqty = txt_warehouse_rack_transfer_quantity.text.toString()

        val map = mutableMapOf<String, Any>()
        if (rackid.isNotEmpty()){
            map["rackid"] = rackid
        }
        if (pcode.isNotEmpty()){
            map["productCode"] = pcode
        }
        if(rackqty.isNotEmpty()){
            map["inQuantity"] = rackqty
        }
        if (rackdate.isNotEmpty()){
            map["inDate"] = rackdate
        }
        return map
    }

    private fun updateRack(warehouse: Warehouse, newPersonMap: Map<String, Any>) = CoroutineScope(Dispatchers.IO).launch {
        val rackQuery = warehouseCollectionRef
//            .whereEqualTo("rackid", warehouse.rackid)
            .whereEqualTo("productCode", warehouse.productCode)
            .get()
            .await()

        if (rackQuery.documents.isNotEmpty()){
            for (document in rackQuery) {
                try {
                    warehouseCollectionRef.document(document.id).set(newPersonMap, SetOptions.merge()).await()
                    Toast.makeText(activity, "Data update failed", Toast.LENGTH_SHORT).show()
                    activity?.onBackPressed()
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(activity, "Successfully update data", Toast.LENGTH_SHORT).show()
                        activity?.onBackPressed()
                    }
                }
            }
        }
        else{
            withContext(Dispatchers.Main){
                Toast.makeText(activity, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete){
            val rack = getOldRack()
            deleteRack(rack)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteRack(warehouse: Warehouse) = CoroutineScope(Dispatchers.IO).launch {
        val rackQuery = warehouseCollectionRef
                .whereEqualTo("rackid", warehouse.rackid)
                .get()
                .await()

        if (rackQuery.documents.isNotEmpty()){
            for(document in rackQuery) {
                try{
                    warehouseCollectionRef.document(document.id).delete().await()
                    Toast.makeText(activity, "Data delete failed", Toast.LENGTH_SHORT).show()
                    /*personCollectionRef.document(document.id).update(mapOf(
                        "firstName" to FieldValue.delete()
                    ))*/
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(activity, "Data deleted", Toast.LENGTH_SHORT).show()
                        activity?.onBackPressed()
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

    lateinit var btnTransferBarcode: ImageButton
    lateinit var txtTransferRackID: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnTransferBarcode = view.findViewById(R.id.btn_warehouse_rack_transfer_scan)
        txtTransferRackID = view.findViewById(R.id.txt_warehouse_rack_transfer_rackid)

        val simpleDateFormat = SimpleDateFormat("EEE, MMM d, ''yy")
        val currentDateAndTime: String = simpleDateFormat.format(Date())
        view.txt_warehouse_rack_transfer_date.setText(currentDateAndTime)

        btnTransferBarcode.setOnClickListener {
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

                txtTransferRackID.text = result.contents

                Log.d("Fragment", "$result")
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}