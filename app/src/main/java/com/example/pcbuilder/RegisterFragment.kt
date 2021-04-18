package com.example.pcbuilder

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.pcbuilder.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {
    private var binding: FragmentRegisterBinding? = null
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        //save inflated view to fragmentBinding
        val fragmentBinding = FragmentRegisterBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()

        fragmentBinding.btnRegister.setOnClickListener {
            registerUser()
        }

        fragmentBinding.txtRegisterBack.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
//            val intent = Intent(context, LoginActivity::class.java)
//            startActivity(intent)
        }

        //initialize to binding
        binding = fragmentBinding
        //create view with inflated fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            registerFragment = this@RegisterFragment
        }
    }

    fun registerUser(){
        if(txtRegisterEmail.text.toString().isEmpty()){
            txtRegisterEmail.error = "Please enter your email"
            txtRegisterEmail.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(txtRegisterEmail.text.toString()).matches()){
            txtRegisterEmail.error = "Please enter a valid email"
            txtRegisterEmail.requestFocus()
            return
        }
        if(txtRegisterPassword.text.toString().isEmpty()){
            txtRegisterPassword.error = "Please enter your password"
            txtRegisterPassword.requestFocus()
            return
        }
        if(!txtRegisterPassword.text.toString().equals(txtRegisterConfirmPassword.text.toString())){
            txtRegisterConfirmPassword.error = "Please confirm your password"
            txtRegisterConfirmPassword.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(txtRegisterEmail.text.toString(), txtRegisterPassword.text.toString())
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        val user: FirebaseUser? = auth.currentUser

                        user!!.sendEmailVerification()
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val intent = Intent(context, LoginActivity::class.java)
                                        startActivity(intent)
//                                startActivity(Intent(this, LoginFragment::class.java))
//                                finish()
                                    }
                                }
//                    updateUI(user)
                    } else {
                        Toast.makeText(context, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
//                    updateUI(null)
                    }
                }
    }
}