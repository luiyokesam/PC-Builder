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
import com.example.pcbuilder.data.Warehouse
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.fragment_item_list_add.*
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
            for(document in querySnapshot.documents) {
                val warehouse = document.toObject<Warehouse>()
                if (warehouse != null) {
                    sb.append("Product Code : ${warehouse.productCode} \n")
                    sb.append("Quantity : ${warehouse.inQuantity}\n")
                    sb.append("Date StockIn : ${warehouse.inDate}\n")
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

    lateinit var btnReportBarcode: ImageButton
    lateinit var txtReportRackID: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnReportBarcode = view.findViewById(R.id.btn_Scan_Report)
        txtReportRackID = view.findViewById(R.id.txt_report_search)


        btnReportBarcode.setOnClickListener {
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

                txtReportRackID.text = result.contents

                Log.d("Fragment", "$result")
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}