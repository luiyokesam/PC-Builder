package com.example.pcbuilder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pcbuilder.adapter.StockInAdapter
import com.example.pcbuilder.adapter.StockOutAdapter
import com.example.pcbuilder.adapter.WarehouseAdapter
import com.example.pcbuilder.model.StockInModel
import com.example.pcbuilder.model.StockOutModel
import com.example.pcbuilder.model.WarehouseModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_stock_in.view.*
import kotlinx.android.synthetic.main.fragment_stock_in.view.stock_in_list
import kotlinx.android.synthetic.main.fragment_stock_out.view.*

class StockOutFragment : Fragment() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance();
    private val collectionReference: CollectionReference = db.collection("warehouse");
    var warehouseAdapter: WarehouseAdapter? = null;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_stock_out, container, false)

//        view.btn_stockin_float.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_stockOutFragment_to_stockOutTransferFragment) }

        setUpRecyclerView(view.stock_out_list)
        return view
    }

    private fun setUpRecyclerView(recyclerview: RecyclerView) {
        val query : Query = collectionReference;
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
}