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


class AddProductFragment : Fragment() {

    private var _binding: FragmentAddProductBinding? = null
    private  val binding get() = _binding!!

    private lateinit var viewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)

        viewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.result.observe(viewLifecycleOwner, Observer {
            val message = if (it == null){
                getString(R.string.added_product)
            }else{
                getString(R.string.error, it.message)
            }

            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

        })

        binding.buttonAdd.setOnClickListener {
            val barcode = binding.editTextBarcode.text.toString().trim()
            val productName = binding.editTextProductName.text.toString().trim()
            val companyName = binding.editTextCompanyName.text.toString().trim()
            val productType = binding.editTextProductType.text.toString().trim()
            val productPrice = binding.editTextProductPrice.text.toString().trim()

            if(barcode.isEmpty()){
                binding.editTextBarcode.error = "This field is required"
                return@setOnClickListener
            }
            if(productName.isEmpty()){
                binding.editTextProductName.error = "This field is required"
                return@setOnClickListener
            }
            if(companyName.isEmpty()){
                binding.editTextCompanyName.error = "This field is required"
                return@setOnClickListener
            }
            if(productType.isEmpty()){
                binding.editTextProductType.error = "This field is required"
                return@setOnClickListener
            }
            if(productPrice.isEmpty()){
                binding.editTextProductPrice.error = "This field is required"
                return@setOnClickListener
            }

            val product = Product()
            product.barcode = barcode
            product.productName = productName
            product.companyName = companyName
            product.productType = productType
            product.productPrice = productPrice

            viewModel.addProduct(product)
        }

    }


}