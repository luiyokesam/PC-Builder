package com.example.pcbuilder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener {
            registerUser()
        }

        txtRegisterBack.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
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
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user: FirebaseUser? = auth.currentUser

                    user!!.sendEmailVerification()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(this, LoginFragment::class.java))
                                finish()
                            }
                        }
//                    updateUI(user)
                } else {
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
//                    updateUI(null)
                }
            }
    }
}