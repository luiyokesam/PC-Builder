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
import java.text.SimpleDateFormat
import java.util.*


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
        var i = 0
        val simpleDateFormat = SimpleDateFormat("h:mm a")
        val simpleDateFormat2 = SimpleDateFormat("EEE, MMM d, ''yy")
        val currentDateAndTime: String = simpleDateFormat.format(Date())
        val currentDateAndTime2: String = simpleDateFormat2.format(Date())
        try {
            val querySnapshot = reportCollectionRef
                .whereEqualTo("rackid", rackID)
                .get()
                .await()
            val sb = StringBuilder()
            sb.append("------------------------------------------------------------------------\n")
            sb.append("Report\n")
            sb.append("------------------------------------------------------------------------\n")
            sb.append("Date: ${currentDateAndTime}\t\t\t\t\tTime: ${currentDateAndTime2}\n")
            sb.append("------------------------------------------------------------------------\n")
            sb.append("Product Code \t\t\t\t\t Date StockIn \t\t\t\t\t Qty\n")
            sb.append("------------------------------------------------------------------------\n")
            for(document in querySnapshot.documents) {
                val warehouse = document.toObject<Warehouse>()
                if (warehouse != null) {
                    sb.append("${warehouse.productCode} \t\t\t\t\t\t ${warehouse.inQuantity} \t\t\t\t\t\t ${warehouse.inDate}\n")
                    sb.append("________________________________________________________________________\n")
                    i = i+1
                }
            }
            sb.append("Total Item : ${i} \n")
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