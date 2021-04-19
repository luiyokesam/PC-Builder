package com.example.pcbuilder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.pcbuilder.R
import com.example.pcbuilder.StockInFragmentDirections
import com.example.pcbuilder.data.StockOut
import com.example.pcbuilder.model.StockOutModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.recyclerview_stockin.view.*

class StockOutAdapter(options: FirestoreRecyclerOptions<StockOutModel>) :
    FirestoreRecyclerAdapter<StockOutModel, StockOutAdapter.StockOutAdapterVH>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockOutAdapterVH {
        return StockOutAdapterVH(LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_warehouse, parent, false))
    }

    override fun onBindViewHolder(holder: StockOutAdapterVH, position: Int, model: StockOutModel) {
//        holder.outbarcode.text = model.productCode
//        holder.outquantity.text = model.outQuantity
//        holder.outdate.text = model.outDate
//
//        holder.itemView.stockin_rowlayout.setOnClickListener{
//            val currentstockout = StockOut(holder.inbarcode.text as String, holder.outQuantity.text as String, holder.outDate.text as String)
//            val action = StockOutFragmentDirections.actionStockInFragmentToStockTransferFragment(currentstockout)
//            holder.itemView.findNavController().navigate(action)
//        }
    }

    class StockOutAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var inbarcode = itemView.txt_rv_stockin_barcode
        var inquantity = itemView.txt_rv_stockin_quantity
        var indate = itemView.txt_rv_stockin_date
    }
}