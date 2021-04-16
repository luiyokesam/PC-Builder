package com.example.pcbuilder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pcbuilder.data.Product
import com.example.pcbuilder.databinding.RecyclerViewProductBinding

class ProductAdapter: RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    var products = mutableListOf<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerViewProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textViewBarcode.text = products[position].barcode
        holder.binding.textViewProductName.text = products[position].productName
        holder.binding.textViewCompanyName.text = products[position].companyName
        holder.binding.textViewProductType.text = products[position].productType
        holder.binding.textViewProductPrice.text = products[position].productPrice

    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun addProduct(product: Product){
        if (!products.contains(product)){
            products.add(product)
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: RecyclerViewProductBinding): RecyclerView.ViewHolder(binding.root){

    }
}