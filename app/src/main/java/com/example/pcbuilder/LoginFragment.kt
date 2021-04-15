package com.example.pcbuilder

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.pcbuilder.databinding.FragmentLoginBinding
import com.example.pcbuilder.login.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*
import androidx.lifecycle.Observer

class LoginFragment : Fragment() {
    companion object{
        const val TAG = "LogInFragment"
        const val SIGN_IN_RESULT_CODE = 1001
    }

    private val viewModel by viewModels<LoginViewModel>()
    private var binding: FragmentLoginBinding? = null
    private lateinit var navController: NavController
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //save inflated view to fragmentBinding
        val fragmentBinding = FragmentLoginBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()

        fragmentBinding.txtLoginForgotPassword.setOnClickListener{ forgotpassword() }
        fragmentBinding.btnLogin.setOnClickListener { login() }
        fragmentBinding.txtLoginSignUp.setOnClickListener {
            val intent = Intent(context, RegisterActivity::class.java)
            startActivity(intent)
        }


        //initialize to binding
        binding = fragmentBinding
        //create view with inflated fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        observeAuthenticationState()

        navController = findNavController()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun login(){
//        //providers
//        val providers = arrayListOf(
//            AuthUI.IdpConfig.EmailBuilder().build()
////            , AuthUI.IdpConfig.GoogleBuilder().build()
//        )
//
//        //sign in intent
//        startActivityForResult(
//            AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(
//                providers
//            ).build(), SIGN_IN_RESULT_CODE
//        )

        val email = txtLoginEmail.text.toString()
        val password = txtLoginPassword.text.toString()

        if(txtLoginEmail.text.toString().isEmpty()){
            txtLoginEmail.error = "Please enter your email."
            txtLoginEmail.requestFocus()
            return
        }
        if(txtLoginPassword.text.toString().isEmpty()){
            txtLoginPassword.error = "Please enter your password."
            txtLoginPassword.requestFocus()
            return
        }
        if(email.isNotEmpty() && password.isNotEmpty()){
            //GlobalScope.launch(Dispatchers.IO) {
            //try {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(context, "Login successfully!", Toast.LENGTH_SHORT).show()

                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(
                        context,
                        task.exception!!.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
                //}

//                    withContext(Dispatchers.Main){
//                        Toast.makeText(context, "You are logged in!", Toast.LENGTH_SHORT).show()
//
//                        val intent = Intent(context, MainActivity::class.java)
//                        startActivity(intent)
//                    }

//                }catch(e: Exception){
//                    withContext(Dispatchers.Main){
//                        //firebase error
//                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
//                    }
//                }
            }
        }

    }
    //    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == SIGN_IN_RESULT_CODE){
//            val response = IdpResponse.fromResultIntent(data)
//            if (resultCode == Activity.RESULT_OK) {
//                // Successfully signed in user.
//                Log.i(
//                    TAG,
//                    "Successfully signed in user " +
//                            "${FirebaseAuth.getInstance().currentUser?.displayName}!"
//                )
//
//            } else {
//                // Sign in failed. If response is null the user canceled the sign-in flow using
//                // the back button. Otherwise check response.getError().getErrorCode() and handle
//                // the error.
//                Log.i(TAG, "Sign in unsuccessful ${response?.error?.errorCode}")
//            }
//        }
//    }

//    private fun observeAuthenticationState(){
//        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
//            when(authenticationState){
//                LoginViewModel.AuthenticationState.AUTHENTICATED ->{
//                    val intent = Intent(this.context, MainActivity::class.java)
//                    startActivity(intent)
//                }
//            }
//        })
//    }

    private fun forgotpassword(){
        findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
    }
}