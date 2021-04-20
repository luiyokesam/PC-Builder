package com.example.pcbuilder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pcbuilder.adapter.StockInAdapter
import com.example.pcbuilder.model.StockInModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.fragment_item_list_add.*
import kotlinx.android.synthetic.main.fragment_stock_in.*
import kotlinx.android.synthetic.main.fragment_stock_in.view.*
import kotlinx.android.synthetic.main.fragment_stock_in_details.*
import kotlinx.android.synthetic.main.fragment_stock_transfer.view.*

class StockInFragment : Fragment() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance();
    private val collectionReference: CollectionReference = db.collection("stockin");
    var stockinAdapter: StockInAdapter? = null;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_stock_in, container, false)

        view.btn_stockin_float.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_stockInFragment_to_stockInDetailsFragment) }

        setUpRecyclerView(view.stock_in_list)

        return view
    }

    private fun setUpRecyclerView(recyclerview: RecyclerView) {
        val query : Query = collectionReference.orderBy("productCode")
        val firestoreRecyclerOptions: FirestoreRecyclerOptions<StockInModel> = FirestoreRecyclerOptions.Builder<StockInModel>()
            .setQuery(query, StockInModel::class.java)
            .build()

        stockinAdapter = StockInAdapter(firestoreRecyclerOptions);

        recyclerview.layoutManager = LinearLayoutManager(this.context)
        recyclerview.adapter = stockinAdapter
    }

    override fun onStart() {
        super.onStart()
        stockinAdapter!!.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        stockinAdapter!!.stopListening()
    }

    lateinit var btnStockBarcode: ImageButton
    lateinit var txtStockCode: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnStockBarcode = view.findViewById(R.id.btn_stockin_scan)
        txtStockCode = view.findViewById(R.id.txt_stockin_search)


        btnStockBarcode.setOnClickListener {
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

                txtStockCode.text = result.contents
                setUpRecyclerView1(stock_in_list)

                Log.d("Fragment", "$result")
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun setUpRecyclerView1(recyclerview: RecyclerView) {
        val code = txt_stockin_search.text.toString()
        val query : Query = collectionReference.whereEqualTo("productCode", code)
       // val query : Query = collectionReference.whereEqualTo()
        val firestoreRecyclerOptions: FirestoreRecyclerOptions<StockInModel> = FirestoreRecyclerOptions.Builder<StockInModel>()
                .setQuery(query, StockInModel::class.java)
                .build()

        stockinAdapter = StockInAdapter(firestoreRecyclerOptions);

        recyclerview.layoutManager = LinearLayoutManager(this.context)
        recyclerview.adapter = stockinAdapter
    }
}