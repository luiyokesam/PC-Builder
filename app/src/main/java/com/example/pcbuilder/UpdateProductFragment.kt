package com.example.pcbuilder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.pcbuilder.data.Product
import com.example.pcbuilder.databinding.FragmentAddProductBinding
import com.example.pcbuilder.databinding.FragmentUpdateProductBinding


class UpdateProductFragment(private val product: Product) : Fragment() {

    private var _binding: FragmentUpdateProductBinding? = null
    private  val binding get() = _binding!!

    private lateinit var viewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateProductBinding.inflate(inflater, container, false)

        viewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.txtUpdateproductBarcode.setText(product.barcode)
        binding.txtUpdateproductCname.setText(product.companyName)
        binding.txtUpdateproductName.setText(product.productName)
        binding.txtUpdateproductPtype.setText(product.productType)
        binding.txtUpdateproductPprice.setText(product.productPrice)

        binding.buttonUpdate.setOnClickListener {
            val barcode = binding.txtUpdateproductBarcode.text.toString().trim()
            val companyName = binding.txtUpdateproductCname.text.toString().trim()
            val productName = binding.txtUpdateproductName.text.toString().trim()
            val productType = binding.txtUpdateproductPtype.text.toString().trim()
            val productPrice = binding.txtUpdateproductPprice.text.toString().trim()

            if(barcode.isEmpty()){
                binding.txtUpdateproductBarcode.error = "This field is required"
                return@setOnClickListener
            }
            if(productName.isEmpty()){
                binding.txtUpdateproductCname.error = "This field is required"
                return@setOnClickListener
            }
            if(companyName.isEmpty()){
                binding.txtUpdateproductName.error = "This field is required"
                return@setOnClickListener
            }
            if(productType.isEmpty()){
                binding.txtUpdateproductPtype.error = "This field is required"
                return@setOnClickListener
            }
            if(productPrice.isEmpty()){
                binding.txtUpdateproductPprice.error = "This field is required"
                return@setOnClickListener
            }

            product.barcode = barcode
            product.productName = productName
            product.companyName = companyName
            product.productType = productType
            product.productPrice = productPrice

            viewModel.updateProduct(product)
            Toast.makeText(context, "Product has been update", Toast.LENGTH_SHORT).show()
        }
    }
}