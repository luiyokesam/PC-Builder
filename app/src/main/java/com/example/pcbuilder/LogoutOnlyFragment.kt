package com.example.pcbuilder

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LogoutOnlyFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()
        logout(null)
        return inflater.inflate(R.layout.fragment_logout_only, container, false)
    }

    private fun logout(currentUser : FirebaseUser?){
        auth.signOut()
        val intent = Intent(context, LoginActivity::class.java)
        startActivity(intent)
        Toast.makeText(context, "You are logged out!",
                Toast.LENGTH_SHORT).show()
    }
}