package com.example.pcbuilder

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.pcbuilder.data.Product
import com.example.pcbuilder.data.StockIn
import com.example.pcbuilder.data.Warehouse
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_item_list_update.*
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
        }

        view.txt_stockin_transfer_barcode.setText(args.currentStockIn.productCode)
        view.txt_stockin_transfer_date.setText(args.currentStockIn.inQuantity)
        view.txt_stockin_transfer_quantity.setText(args.currentStockIn.inQuantity)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == R.id.menu_delete){
//            val product = getOldProduct()
//            deleteProduct(product)
//        }
//        return super.onOptionsItemSelected(item)
//    }

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
//                        view.findViewById<>()

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