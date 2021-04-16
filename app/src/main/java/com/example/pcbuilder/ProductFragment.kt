package com.example.pcbuilder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.pcbuilder.databinding.FragmentProductBinding


class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private val adapter = ProductAdapter()

    private lateinit var viewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewProducts.adapter = adapter

        binding.addProductButton.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_productFragment_to_addProductFragment)
        }

        viewModel.product.observe(viewLifecycleOwner, Observer {
            adapter.addProduct(it)
        })

        viewModel.getRealtimeUpdate()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}