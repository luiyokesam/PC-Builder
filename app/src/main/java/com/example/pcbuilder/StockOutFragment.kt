package com.example.pcbuilder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pcbuilder.adapter.StockInAdapter
import com.example.pcbuilder.adapter.StockOutAdapter
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class StockOutFragment : Fragment() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance();
    private val collectionReference: CollectionReference = db.collection("stockout");
    var stockoutAdapter: StockOutAdapter? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stock_out, container, false)
    }


}