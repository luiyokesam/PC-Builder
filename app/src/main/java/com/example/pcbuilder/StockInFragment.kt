package com.example.pcbuilder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import kotlinx.android.synthetic.main.fragment_stock_in.view.*
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
        val query : Query = collectionReference;
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
}