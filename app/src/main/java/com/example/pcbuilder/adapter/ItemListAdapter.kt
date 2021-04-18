package com.example.pcbuilder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.pcbuilder.ItemListFragmentDirections
import com.example.pcbuilder.R
import com.example.pcbuilder.data.Product
import com.example.pcbuilder.model.ItemLIstModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.recyclerview_itemlist.view.*

class ItemListAdapter(options: FirestoreRecyclerOptions<ItemLIstModel>) :
    FirestoreRecyclerAdapter<ItemLIstModel, ItemListAdapter.ItemListAdapterVH>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListAdapterVH {
        return  ItemListAdapterVH(LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_itemlist, parent, false))
    }

    override fun onBindViewHolder(holder: ItemListAdapterVH, position: Int, model: ItemLIstModel) {
        holder.pro_Code.text = model.productCode
        holder.pro_Name.text = model.productName
        holder.pro_Company.text = model.productCompany
        holder.pro_Type.text = model.productType
        holder.pro_Price.text = model.productPrice.toString()

        holder.itemView.list_item_row.setOnClickListener{
            val currentprod = Product(holder.pro_Code.text as String, holder.pro_Name.text as String, holder.pro_Company.text as String, holder.pro_Type.text as String, holder.pro_Price.text as String)
            val action = ItemListFragmentDirections.actionItemListFragmentToItemListUpdateFragment(currentprod)
            holder.itemView.findNavController().navigate(action)
        }
    }

    class ItemListAdapterVH(itemView: View): RecyclerView.ViewHolder(itemView) {
        var pro_Code = itemView.txt_rv_addproduct_barcode
        var pro_Name = itemView.txt_rv_addproduct_productname
        var pro_Company = itemView.txt_rv_addproduct_companyname
        var pro_Type = itemView.txt_rv_addproduct_producttype
        var pro_Price = itemView.txt_rv_addproduct_productprice
    }
}

