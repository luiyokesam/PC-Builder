package com.example.pcbuilder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.pcbuilder.data.Warehouse
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_report.*
import kotlinx.android.synthetic.main.fragment_report.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class ReportFragment : Fragment() {

    private val reportCollectionRef = Firebase.firestore.collection("warehouse")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_report, container, false)

        view.btn_report_generate.setOnClickListener {
            retrieveReport()
        }

        return view
    }

    private fun retrieveReport() = CoroutineScope(Dispatchers.IO).launch {
        val rackID = txt_report_search.text.toString()
        try {
            val querySnapshot = reportCollectionRef
                .whereEqualTo("rackid", rackID)
                .get()
                .await()
            val sb = StringBuilder()
            sb.append("report\n")
            for(document in querySnapshot.documents) {
                val warehouse = document.toObject<Warehouse>()
                if (warehouse != null) {
                    sb.append("${warehouse.productCode} \t ${warehouse.inQuantity} \t ${warehouse.inDate}\n")
                }
            }
            withContext(Dispatchers.Main) {
                txt_report_data.text = sb.toString()
            }
        } catch(e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(activity, "Successfully Generate Report", Toast.LENGTH_SHORT).show()
            }
        }
    }


}