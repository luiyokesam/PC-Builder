package com.example.pcbuilder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.pcbuilder.ui.home.HomeFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        // Initialize Firebase Auth
//        auth = FirebaseAuth.getInstance()
//
//        btnRegister.setOnClickListener {
//            startActivity(Intent(this, RegisterActivity::class.java))
//            finish()
//        }
//
//        btnLogin.setOnClickListener {
//            login()
//        }
    }

//    public override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        updateUI(currentUser)
//    }
//
//    private fun login(){
//        if(txtLoginEmail.text.toString().isEmpty()){
//            txtLoginEmail.error = "Please enter your email"
//            txtLoginEmail.requestFocus()
//            return
//        }
//        if(txtLoginPassword.text.toString().isEmpty()){
//            txtLoginPassword.error = "Please enter your password"
//            txtLoginPassword.requestFocus()
//            return
//        }
//        auth.signInWithEmailAndPassword(txtLoginEmail.text.toString(), txtLoginPassword.text.toString())
//                .addOnCompleteListener(this) { task ->
//                    if (task.isSuccessful) {
//                        val user = auth.currentUser
//                        updateUI(user)
//                        Toast.makeText(baseContext, "Login successfully.",
//                                Toast.LENGTH_SHORT).show()
//                    } else {
//                        Toast.makeText(baseContext, "Authentication failed.",
//                                Toast.LENGTH_SHORT).show()
//                        updateUI(null)
//                    }
//                }
//    }
//
//    private fun updateUI(currentUser : FirebaseUser?){
//        if(currentUser != null){
//            if(currentUser.isEmailVerified){
//                startActivity(Intent(this, MainActivity::class.java))
////                finish()
//            }
//            else {
//                Toast.makeText(baseContext, "Please verify your email.",
//                        Toast.LENGTH_SHORT).show()
//            }
//        }
////        else {
////            Toast.makeText(baseContext, "Authentication failed.",
////                Toast.LENGTH_SHORT).show()
//////            updateUI(null)
////        }
//    }
}