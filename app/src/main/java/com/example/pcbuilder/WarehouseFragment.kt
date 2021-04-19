package com.example.pcbuilder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_warehouse.view.*

class WarehouseFragment : Fragment() {
    companion object{
        var rack: String? = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_warehouse, container, false)

        view.btn_a1.setOnClickListener {
            rack = "A1"
            Navigation.findNavController(view).navigate(R.id.warehouseRackFragment)
            (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.rack_title, "A1")
        }
        view.btn_a2.setOnClickListener {
            rack = "A2"
            Navigation.findNavController(view).navigate(R.id.warehouseRackFragment)
            (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.rack_title, "A2")
        }
        view.btn_a3.setOnClickListener {
            rack = "A3"
            Navigation.findNavController(view).navigate(R.id.warehouseRackFragment)
            (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.rack_title, "A3")
        }
        view.btn_a4.setOnClickListener {
            rack = "A4"
            Navigation.findNavController(view).navigate(R.id.warehouseRackFragment)
            (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.rack_title, "A4")
        }

        view.btn_b1.setOnClickListener {
            rack = "B1"
            Navigation.findNavController(view).navigate(R.id.warehouseRackFragment)
            (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.rack_title, "B1")
        }
        view.btn_b2.setOnClickListener {
            rack = "B2"
            Navigation.findNavController(view).navigate(R.id.warehouseRackFragment)
            (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.rack_title, "B2")
        }
        view.btn_b3.setOnClickListener {
            rack = "B3"
            Navigation.findNavController(view).navigate(R.id.warehouseRackFragment)
            (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.rack_title, "B3")
        }
        view.btn_b4.setOnClickListener {
            rack = "B4"
            Navigation.findNavController(view).navigate(R.id.warehouseRackFragment)
            (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.rack_title, "B4")
        }

        view.btn_c1.setOnClickListener {
            rack = "C1"
            Navigation.findNavController(view).navigate(R.id.warehouseRackFragment)
            (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.rack_title, "C1")
        }
        view.btn_c2.setOnClickListener {
            rack = "C2"
            Navigation.findNavController(view).navigate(R.id.warehouseRackFragment)
            (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.rack_title, "C2")
        }
        view.btn_c3.setOnClickListener {
            rack = "C3"
            Navigation.findNavController(view).navigate(R.id.warehouseRackFragment)
            (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.rack_title, "C3")
        }
        view.btn_c4.setOnClickListener {
            rack = "C4"
            Navigation.findNavController(view).navigate(R.id.warehouseRackFragment)
            (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.rack_title, "C4")
        }

        return view
    }
}