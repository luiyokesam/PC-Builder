package com.example.pcbuilder

import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pcbuilder.data.NODE_PRODUCTS
import com.example.pcbuilder.data.Product
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlin.contracts.contract

class ProductViewModel: ViewModel() {

    private val dbproducts = FirebaseDatabase.getInstance().getReference(NODE_PRODUCTS)

    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?> get() = _result

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> get() = _product

    fun addProduct(product: Product){
        product.id = dbproducts.push().key

        dbproducts.child(product.id!!).setValue(product).addOnCompleteListener{
            if(it.isSuccessful){
                _result.value = null
            }else{
                _result.value = it.exception
            }

        }
    }

    private val childEventListener = object: ChildEventListener{
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val product = snapshot.getValue(Product::class.java)
            product?.id = snapshot.key
            _product.value = product!!
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            val product = snapshot.getValue(Product::class.java)
            product?.id = snapshot.key
            _product.value = product!!
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            val product = snapshot.getValue(Product::class.java)
            product?.id = snapshot.key
            product?.isDeleted = true
            _product.value = product!!
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            TODO("Not yet implemented")
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }

    }

    fun getRealtimeUpdate(){
        dbproducts.addChildEventListener(childEventListener)
    }

    fun updateProduct(product: Product){
        dbproducts.child(product.id!!).setValue(product)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    _result.value = null
                }else{
                    _result.value = it.exception
                }
            }
    }

    fun deleteProduct(product: Product){
        dbproducts.child(product.id!!).setValue(null)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    _result.value = null
                }else{
                    _result.value = it.exception
                }
            }
    }


    override fun onCleared() {
        super.onCleared()
        dbproducts.removeEventListener(childEventListener)
    }

}