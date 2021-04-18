package com.example.pcbuilder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.pcbuilder.R
import com.example.pcbuilder.StockTransferFragmentDirections
import com.example.pcbuilder.model.WarehouseModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.recyclerview_warehouse.view.*

class WarehouseAdapter(options: FirestoreRecyclerOptions<WarehouseModel>) :
        FirestoreRecyclerAdapter<WarehouseModel, WarehouseAdapter.WarehouseAdapterVH>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WarehouseAdapter.WarehouseAdapterVH {
        return WarehouseAdapter.WarehouseAdapterVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_warehouse, parent, false)
        )
    }

    override fun onBindViewHolder(holder: WarehouseAdapterVH, position: Int, model: WarehouseModel) {
        holder.transrackid.text = model.rackid
        holder.transbarcode.text = model.productCode
        holder.transquantity.text = model.inQuantity.toString()
        holder.transdate.text = model.inDate

//        holder.itemView.warehouse_rowlayout.setOnClickListener{
////            val action = StockInFragmentDirections.actionItemListFragmentToItemListUpdateFragment(
//            val action = StockTransferFragmentDirections.actionStockTransferFragmentToWarehouseFragment(
//                model.rackid.toString()
//            )
//            holder.itemView.findNavController().navigate(action)
//        }
    }

    class WarehouseAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var transrackid = itemView.txt_rv_warehouse_rackid
        var transbarcode = itemView.txt_rv_warehouse_barcode
        var transquantity = itemView.txt_rv_warehouse_quantity
        var transdate = itemView.txt_rv_warehouse_date
    }
}