package com.example.pcbuilder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pcbuilder.R
import com.example.pcbuilder.model.StockInModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.recyclerview_stockin.view.*

class StockInAdapter(options: FirestoreRecyclerOptions<StockInModel>) :
        FirestoreRecyclerAdapter<StockInModel, StockInAdapter.StockInAdapterVH>(options){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockInAdapterVH {
            return  StockInAdapterVH(LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_stockin, parent, false))
    }

    override fun onBindViewHolder(holder: StockInAdapterVH, position: Int, model: StockInModel) {
        holder.inbarcode.text = model.productCode
        holder.inquantity.text = model.inQuantity.toString()
        holder.indate.text = model.inDate

        holder.itemView.stockin_rowlayout.setOnClickListener{

        }
    }

    class StockInAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var inbarcode = itemView.txt_rv_stockin_barcode
        var inquantity = itemView.txt_rv_stockin_quantity
        var indate = itemView.txt_rv_stockin_date
    }

}