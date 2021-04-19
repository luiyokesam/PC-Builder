package com.example.pcbuilder

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pcbuilder.WarehouseFragment.Companion.rack
import com.example.pcbuilder.adapter.StockInAdapter
import com.example.pcbuilder.adapter.WarehouseAdapter
import com.example.pcbuilder.adapter.WarehouseRackAdapter
import com.example.pcbuilder.data.Warehouse
import com.example.pcbuilder.model.StockInModel
import com.example.pcbuilder.model.WarehouseModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_warehouse.view.*
import kotlinx.android.synthetic.main.fragment_warehouse_rack.view.*
import kotlinx.coroutines.tasks.await

class WarehouseRackFragment : Fragment() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance();
    private val collectionReference: CollectionReference = db.collection("warehouse");
    var warehouserackAdapter: WarehouseRackAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_warehouse_rack, container, false)


        filterRack(rack.toString(), view.warehouse_rack_list)
//        setUpRecyclerView(view.warehouse_rack_list)
        return view
    }

     fun filterRack(rackid : String, recyclerview: RecyclerView) {
        val query : Query = collectionReference
                .whereEqualTo("rackid", rackid)
        val firestoreRecyclerOptions: FirestoreRecyclerOptions<WarehouseModel> = FirestoreRecyclerOptions.Builder<WarehouseModel>()
                .setQuery(query, WarehouseModel::class.java)
                .build()

         warehouserackAdapter = WarehouseRackAdapter(firestoreRecyclerOptions);

        recyclerview.layoutManager = LinearLayoutManager(this.context)
        recyclerview.adapter = warehouserackAdapter
    }

//    private fun setUpRecyclerView(recyclerview: RecyclerView) {
//        val query : Query = collectionReference
//                .whereEqualTo("rackid", "A2")
//        val firestoreRecyclerOptions: FirestoreRecyclerOptions<WarehouseModel> = FirestoreRecyclerOptions.Builder<WarehouseModel>()
//                .setQuery(query, WarehouseModel::class.java)
//                .build()
//
//        warehouseAdapter = WarehouseAdapter(firestoreRecyclerOptions);
//
//        recyclerview.layoutManager = LinearLayoutManager(this.context)
//        recyclerview.adapter = warehouseAdapter
//    }

    override fun onStart() {
        super.onStart()
        warehouserackAdapter!!.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        warehouserackAdapter!!.stopListening()
    }
}