package com.example.pcbuilder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pcbuilder.adapter.ItemListAdapter
import com.example.pcbuilder.model.ItemLIstModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.fragment_item_list.*
import kotlinx.android.synthetic.main.fragment_item_list.view.*
import kotlinx.android.synthetic.main.fragment_stock_in.*
import kotlinx.coroutines.tasks.await

class ItemListFragment : Fragment() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val collectionReference: CollectionReference = db.collection("products")
    var itemListAdapter : ItemListAdapter? =null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        view.add_ProductButton.setOnClickListener { Navigation.findNavController(view).navigate(R.id.itemListAddFragment) }

        setUpRecyclerView(view.recycler_view_products)
        return view
    }

    private fun setUpRecyclerView(recyclerview: RecyclerView) {
        //val query : Query = collectionReference.whereEqualTo("productCode", "121")
        val query : Query = collectionReference.orderBy("productCode")
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

    lateinit var btnItemBarcode: ImageButton
    lateinit var txtItemCode: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnItemBarcode = view.findViewById(R.id.btn_itemlist_scan)
        txtItemCode = view.findViewById(R.id.txt_itemlist_search)


        btnItemBarcode.setOnClickListener {
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

                txtItemCode.text = result.contents
                setUpRecyclerView1(recycler_view_products)

                Log.d("Fragment", "$result")
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun setUpRecyclerView1(recyclerview: RecyclerView) {
        //val query : Query = collectionReference.whereEqualTo("productCode", "121")
        val code = txt_itemlist_search.text.toString()
        val query : Query = collectionReference.whereEqualTo("productCode", code)
        val firestoreRecyclerOptions: FirestoreRecyclerOptions<ItemLIstModel> = FirestoreRecyclerOptions.Builder<ItemLIstModel>()
                .setQuery(query, ItemLIstModel::class.java)
                .build();

        itemListAdapter = ItemListAdapter(firestoreRecyclerOptions);

        recyclerview.layoutManager = LinearLayoutManager(this.context)
        recyclerview.adapter = itemListAdapter
    }


}