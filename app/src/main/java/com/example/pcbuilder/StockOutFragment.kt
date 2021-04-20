package com.example.pcbuilder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pcbuilder.adapter.WarehouseAdapter
import com.example.pcbuilder.model.WarehouseModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.fragment_item_list.*
import kotlinx.android.synthetic.main.fragment_stock_out.*
import kotlinx.android.synthetic.main.fragment_stock_out.view.*
import kotlinx.android.synthetic.main.fragment_warehouse_rack_transfer.view.*
import java.text.SimpleDateFormat
import java.util.*

class StockOutFragment : Fragment() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance();
    private val collectionReference: CollectionReference = db.collection("warehouse");
    var warehouseAdapter: WarehouseAdapter? = null
//    var stockoutAdapter: StockOutAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_stock_out, container, false)

//        view.btn_stockin_float.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_stockOutFragment_to_stockOutTransferFragment) }

        setUpRecyclerView(view.stock_out_list)
        return view
    }

    private fun setUpRecyclerView(recyclerview: RecyclerView) {
        val query : Query = collectionReference.orderBy("rackid")
        val firestoreRecyclerOptions: FirestoreRecyclerOptions<WarehouseModel> = FirestoreRecyclerOptions.Builder<WarehouseModel>()
            .setQuery(query, WarehouseModel::class.java)
            .build()

        warehouseAdapter = WarehouseAdapter(firestoreRecyclerOptions);

        recyclerview.layoutManager = LinearLayoutManager(this.context)
        recyclerview.adapter = warehouseAdapter
    }

    override fun onStart() {
        super.onStart()
        warehouseAdapter!!.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        warehouseAdapter!!.stopListening()
    }


    lateinit var btnStockOutBarcode: ImageButton
    lateinit var txtStockOutCode: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnStockOutBarcode = view.findViewById(R.id.btn_stockout_scan)
        txtStockOutCode = view.findViewById(R.id.txt_stockout_search)


        btnStockOutBarcode.setOnClickListener {
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

                txtStockOutCode.text = result.contents
                setUpRecyclerView1(stock_out_list)

                Log.d("Fragment", "$result")
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


    private fun setUpRecyclerView1(recyclerview: RecyclerView) {
        val code = txt_stockout_search.text.toString()
        val query : Query = collectionReference
                .whereEqualTo("rackid", code)
                .orderBy("rackid")
        val firestoreRecyclerOptions: FirestoreRecyclerOptions<WarehouseModel> = FirestoreRecyclerOptions.Builder<WarehouseModel>()
                .setQuery(query, WarehouseModel::class.java)
                .build()

        warehouseAdapter = WarehouseAdapter(firestoreRecyclerOptions);

        recyclerview.layoutManager = LinearLayoutManager(this.context)
        recyclerview.adapter = warehouseAdapter
    }
}