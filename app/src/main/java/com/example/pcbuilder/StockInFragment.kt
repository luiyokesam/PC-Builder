package com.example.pcbuilder

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pcbuilder.adapter.StockInAdapter
import com.example.pcbuilder.databinding.FragmentLoginBinding
import com.example.pcbuilder.databinding.FragmentStockInBinding
import com.example.pcbuilder.model.StockInModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_stock_in.view.*

class StockInFragment : Fragment() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance();
    private val collectionReference: CollectionReference = db.collection("stockin");
    var stockinAdapter: StockInAdapter? = null;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentStockInBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_stock_in, container, false)

//        view.btn_stockin_float.setOnClickListener {
//            Navigation.findNavController(view).navigate(R.id.action_stockInFragment_to_stockInDetailsFragment)
//        }
        setUpRecyclerView(binding.stockInList)
        binding.btnStockinFloat.setOnClickListener{
            val t = this.requireFragmentManager().beginTransaction()
            val mFrag: Fragment = StockInDetailsFragment(true, null)
            t.replace(R.id.nav_host_fragment, mFrag)
            t.commit()
        }
        return binding.root
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

//    private fun retrieveProduct() = CoroutineScope(Dispatchers.IO).launch {
//        try {
//            val querySnapshot = productCollectionRef.get().await()
//            val sb = StringBuilder()
//            for (document in querySnapshot.documents) {
//                val product = document.toObject<Product>()
//                sb.append("$product\n")
//            }
//            withContext(Dispatchers.Main) {
//                txtProductDisplay.text =sb.toString()
//            }
//        } catch (e: Exception) {
//            withContext(Dispatchers.Main) {
//                Toast.makeText(activity, "Display Fail", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    private fun subscribeToRealtimeUpdate() {
//        productCollectionRef.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
//            firebaseFirestoreException?.let {
//                Toast.makeText(activity, "Realtime Fail", Toast.LENGTH_SHORT).show()
//                return@addSnapshotListener
//            }
//            querySnapshot?.let {
//                val sb = StringBuilder()
//                for(document in it){
//                    val product = document.toObject<Product>()
//                    if (product != null) {
//                        sb.append("$product\n")
//                    }
//                    txtProductDisplay.text = sb.toString()
//                }
//            }
//        }
//    }
}