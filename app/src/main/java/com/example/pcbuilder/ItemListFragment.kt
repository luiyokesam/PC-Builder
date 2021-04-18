package com.example.pcbuilder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pcbuilder.adapter.ItemListAdapter
import com.example.pcbuilder.model.ItemLIstModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_item_list.view.*

class ItemListFragment : Fragment() {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val collectionReference: CollectionReference = db.collection("products")
    var itemListAdapter : ItemListAdapter? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        view.add_ProductButton.setOnClickListener { Navigation.findNavController(view).navigate(R.id.itemListAddFragment) }

        setUpRecyclerView(view.recycler_view_products)

        return view
    }

    private fun setUpRecyclerView(recyclerview: RecyclerView) {

        val query : Query = collectionReference;
        val firestoreRecyclerOptions: FirestoreRecyclerOptions<ItemLIstModel> = FirestoreRecyclerOptions.Builder<ItemLIstModel>()
            .setQuery(query, ItemLIstModel::class.java)
            .build();

        itemListAdapter = ItemListAdapter(firestoreRecyclerOptions);

        recyclerview.layoutManager = LinearLayoutManager(this.context)
        recyclerview.adapter = itemListAdapter
    }

    override fun onStart() {
        super.onStart()
        itemListAdapter!!.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        itemListAdapter!!.stopListening()
    }

}