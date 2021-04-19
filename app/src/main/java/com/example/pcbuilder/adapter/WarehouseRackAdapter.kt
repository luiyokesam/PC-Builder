package com.example.pcbuilder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.pcbuilder.R
import com.example.pcbuilder.WarehouseRackFragmentDirections
import com.example.pcbuilder.data.Warehouse
import com.example.pcbuilder.model.WarehouseModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.recyclerview_warehouse.view.*

class WarehouseRackAdapter(options: FirestoreRecyclerOptions<WarehouseModel>) :
        FirestoreRecyclerAdapter<WarehouseModel, WarehouseRackAdapter.WarehouseRackAdapterVH>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WarehouseRackAdapter.WarehouseRackAdapterVH {
        return WarehouseRackAdapter.WarehouseRackAdapterVH(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.recyclerview_warehouse, parent, false)
        )
    }

    override fun onBindViewHolder(holder: WarehouseRackAdapterVH, position: Int, model: WarehouseModel) {
        holder.warerackrackid.text = model.rackid
        holder.warerackbarcode.text = model.productCode
        holder.warerackquantity.text = model.inQuantity
        holder.warerackdate.text = model.inDate

        holder.itemView.warehouse_rowlayout.setOnClickListener{
            val currentwarerack = Warehouse(holder.warerackrackid.text as String, holder.warerackbarcode.text as String, holder.warerackquantity.text as String, holder.warerackdate.text as String)
//            val action = WarehouseRackTransferFragmentDirections.actionWarehouseRackTransferFragmentToWarehouseRackFragment2(currentwarerack)
            val action = WarehouseRackFragmentDirections.actionWarehouseRackFragmentToWarehouseRackTransferFragment(currentwarerack)
            holder.itemView.findNavController().navigate(action)
        }
    }

    class WarehouseRackAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var warerackrackid = itemView.txt_rv_warehouse_rackid
        var warerackbarcode = itemView.txt_rv_warehouse_barcode
        var warerackquantity = itemView.txt_rv_warehouse_quantity
        var warerackdate = itemView.txt_rv_warehouse_date
    }
}