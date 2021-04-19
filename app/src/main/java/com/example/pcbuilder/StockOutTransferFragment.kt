package com.example.pcbuilder

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pcbuilder.data.StockOut
import com.example.pcbuilder.data.Warehouse
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_stock_out_transfer.*
import kotlinx.android.synthetic.main.fragment_stock_out_transfer.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class StockOutTransferFragment : Fragment() {
    private val warehouseCollectionRef = Firebase.firestore.collection("warehouse")
    private val stockoutCollectionRef = Firebase.firestore.collection("stockout")
    private val args by navArgs<StockOutTransferFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_stock_out_transfer, container, false)

        view.btn_stockout_transfer_transfer.setOnClickListener {
            val outrackid = txt_stockout_transfer_rackid.text.toString()
            val outbarcode = txt_stockout_transfer_barcode.text.toString()
            val outdate = txt_stockout_transfer_date.text.toString()
            val outquantity = txt_stockout_transfer_quantity.text.toString()
            val stockout = StockOut(outrackid, outbarcode, outdate, outquantity)
            saveStockOutTransfer(stockout)

            val warehouse = getOldWarehouse()
            deleteWarehouse(warehouse)
            findNavController().navigate(R.id.action_stockOutTransferFragment_to_stockOutFragment)
//            activity?.onBackPressed()
        }

        view.txt_stockout_transfer_rackid.setText(args.currentStockOut.rackid)
        view.txt_stockout_transfer_barcode.setText(args.currentStockOut.productCode)
        view.txt_stockout_transfer_date.setText(args.currentStockOut.outDate)
        view.txt_stockout_transfer_quantity.setText(args.currentStockOut.outQuantity)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    private fun getOldWarehouse(): Warehouse {
        val rackid = txt_stockout_transfer_rackid.text.toString()
        val pcode = txt_stockout_transfer_barcode.text.toString()

        return Warehouse(rackid, pcode)
    }

    private fun deleteWarehouse(warehouse: Warehouse) = CoroutineScope(Dispatchers.IO).launch {
        val productQuery = warehouseCollectionRef
            .whereEqualTo("rackid", warehouse.rackid)
            .whereEqualTo("productCode", warehouse.productCode)
            .get()
            .await()

        if (productQuery.documents.isNotEmpty()){
            for(document in productQuery) {
                try{
                    warehouseCollectionRef.document(document.id).delete().await()
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

    private fun saveStockOutTransfer(stockout: StockOut) = CoroutineScope(Dispatchers.IO).launch {
        try{
            stockoutCollectionRef.add(stockout).await()
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
}