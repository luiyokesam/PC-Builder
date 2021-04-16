package com.example.pcbuilder

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment.STYLE_NO_TITLE
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.pcbuilder.databinding.FragmentProductBinding


class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private val adapter = ProductAdapter()

    private lateinit var viewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)*/
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

        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewProducts)

    }

    private var simpleCallback = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            var positions = viewHolder.adapterPosition
            var currentProduct = adapter.products[positions]

            when(direction){
                ItemTouchHelper.RIGHT -> {

                }
                ItemTouchHelper.LEFT -> {
                    AlertDialog.Builder(requireContext()).also {
                        it.setTitle("Are you sure you want to delete this product")
                        it.setPositiveButton("Yes"){ dialog, which ->
                            viewModel.deleteProduct(currentProduct)
                            binding.recyclerViewProducts.adapter?.notifyItemRemoved(positions)
                            Toast.makeText(context, "Product has been deleted", Toast.LENGTH_SHORT).show()
                        }
                    }.create().show()
                }
            }
            binding.recyclerViewProducts.adapter?.notifyDataSetChanged()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}