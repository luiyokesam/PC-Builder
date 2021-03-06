package com.example.pcbuilder

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.pcbuilder.data.Product
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_item_list_update.*
import kotlinx.android.synthetic.main.fragment_item_list_update.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class ItemListUpdateFragment: Fragment() {
    private val productCollectionRef = Firebase.firestore.collection("products")
    private val args by navArgs<ItemListUpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_item_list_update, container, false)

        view.button_update.setOnClickListener {
            val oldProduct = getOldProduct()
            val newPersonMap = getNewProductMap()
            updateProduct(oldProduct, newPersonMap)
        }

        view.txt_updateproduct_barcode.setText(args.currentItem.productCode)
        view.txt_updateproduct_cname.setText(args.currentItem.productCompany)
        view.txt_updateproduct_name.setText(args.currentItem.productName)
        view.txt_updateproduct_ptype.setText(args.currentItem.productType)
        view.txt_updateproduct_pprice.setText(args.currentItem.productPrice)

        setHasOptionsMenu(true)
        return view
    }


    private fun getOldProduct(): Product {
        val pcode = txt_updateproduct_barcode.text.toString()

        return Product(pcode)
    }

    private fun getNewProductMap(): Map<String, Any> {
        val pcode = txt_updateproduct_barcode.text.toString()
        val pName = txt_updateproduct_name.text.toString()
        val pCompany = txt_updateproduct_cname.text.toString()
        val pType = txt_updateproduct_ptype.text.toString()
        val pPrice = txt_updateproduct_pprice.text.toString()
        val map = mutableMapOf<String, Any>()
        if (pcode.isNotEmpty()){
            map["productCode"] = pcode
        }
        if (pName.isNotEmpty()){
            map["productName"] = pName
        }
        if(pCompany.isNotEmpty()){
            map["productCompany"] = pCompany
        }
        if (pType.isNotEmpty()){
            map["productType"] = pType
        }
        if (pPrice.isNotEmpty()){
            map["productPrice"] = pPrice
        }
        return map
    }

    private fun updateProduct(product: Product, newPersonMap: Map<String, Any>) = CoroutineScope(Dispatchers.IO).launch {
        val productQuery = productCollectionRef
            .whereEqualTo("productCode", product.productCode)
            .get()
            .await()

        if (productQuery.documents.isNotEmpty()){
            for(document in productQuery) {
                try{
                    productCollectionRef.document(document.id).set(newPersonMap, SetOptions.merge()).await()
                    Toast.makeText(activity, "Data update failed", Toast.LENGTH_SHORT).show()
                    activity?.onBackPressed()
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(activity, "Successfully update data", Toast.LENGTH_SHORT).show()
                        activity?.onBackPressed()
                    }
                }
            }
        }else{
            withContext(Dispatchers.Main){
                Toast.makeText(activity, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete){
            val product = getOldProduct()
            deleteProduct(product)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteProduct(product: Product) = CoroutineScope(Dispatchers.IO).launch {
        val productQuery = productCollectionRef
            .whereEqualTo("productCode", product.productCode)
            .get()
            .await()

        if (productQuery.documents.isNotEmpty()){
            for(document in productQuery) {
                try{
                    productCollectionRef.document(document.id).delete().await()
                    Toast.makeText(activity, "Data delete failed", Toast.LENGTH_SHORT).show()
                    /*personCollectionRef.document(document.id).update(mapOf(
                        "firstName" to FieldValue.delete()
                    ))*/
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(activity, "Data deleted", Toast.LENGTH_SHORT).show()
                        activity?.onBackPressed()
                    }
                }
            }
        }
        else {
            withContext(Dispatchers.Main){
                Toast.makeText(activity, "No data found", Toast.LENGTH_SHORT).show()
            }
        }
    }
}