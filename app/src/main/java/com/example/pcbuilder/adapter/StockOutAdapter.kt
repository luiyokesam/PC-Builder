package com.example.pcbuilder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.pcbuilder.R
import com.example.pcbuilder.StockOutFragmentDirections
import com.example.pcbuilder.data.StockOut
import com.example.pcbuilder.model.StockOutModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.recyclerview_warehouse.view.*

class StockOutAdapter(options: FirestoreRecyclerOptions<StockOutModel>) :
    FirestoreRecyclerAdapter<StockOutModel, StockOutAdapter.StockOutAdapterVH>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockOutAdapterVH {
        return StockOutAdapterVH(LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_warehouse, parent, false))
    }

    override fun onBindViewHolder(holder: StockOutAdapterVH, position: Int, model: StockOutModel) {
        holder.outrackid.text = model.productCode
        holder.outbarcode.text = model.productCode
        holder.outquantity.text = model.outQuantity
        holder.outdate.text = model.outDate

        holder.itemView.warehouse_rowlayout.setOnClickListener{
            val currentstockout = StockOut(holder.outrackid.text as String, holder.outbarcode.text as String, holder.outquantity.text as String, holder.outdate.text as String)
            val action = StockOutFragmentDirections.actionStockOutFragmentToStockOutTransferFragment(currentstockout)
            holder.itemView.findNavController().navigate(action)
        }
    }

    class StockOutAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var outrackid = itemView.txt_rv_warehouse_rackid
        var outbarcode = itemView.txt_rv_warehouse_barcode
        var outquantity = itemView.txt_rv_warehouse_quantity
        var outdate = itemView.txt_rv_warehouse_date
    }
}