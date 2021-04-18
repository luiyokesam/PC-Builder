package com.example.pcbuilder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_item_list_add.view.*
import kotlinx.android.synthetic.main.fragment_item_list_update.view.*


class ItemListUpdateFragment: Fragment() {
//    val args: ConfirmationFragmentArgs by navArgs()
//    val itemid = id

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_item_list_update, container, false)

//        view.txt_updateproduct_barcode.setText(itemid.toString())



        return view
    }


}