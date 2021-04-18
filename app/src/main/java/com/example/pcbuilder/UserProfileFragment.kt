package com.example.pcbuilder

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.pcbuilder.databinding.FragmentUserProfileBinding
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_user_profile.*

class UserProfileFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private var binding: FragmentUserProfileBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //save inflated view to fragmentBinding
        val fragmentBinding = FragmentUserProfileBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()

        //initialize to binding
        binding = fragmentBinding
        //create view with inflated fragmentBinding

        fragmentBinding.btnUserprofileReset.setOnClickListener { resetpassword() }
        fragmentBinding.btnUserprofileLogout.setOnClickListener { logout(null) }

        return fragmentBinding.root
    }

    private fun logout(currentUser : FirebaseUser?){
        auth.signOut()
        val intent = Intent(context, LoginActivity::class.java)
        startActivity(intent)
        Toast.makeText(context, "You are logged out!",
                Toast.LENGTH_SHORT).show()
    }

    private fun resetpassword(){
        if(txt_userprofile_oldpassword.text.toString().isEmpty()){
            txt_userprofile_oldpassword.error = "Please enter your old password"
            txt_userprofile_oldpassword.requestFocus()
            return
        }
        if(txt_userprofile_newpassword.text.toString().isEmpty()){
            txt_userprofile_newpassword.error = "Please enter your new password"
            txt_userprofile_newpassword.requestFocus()
            return
        }
        if(!txt_userprofile_newpassword.text.toString().equals(txt_userprofile_confirmnewpassword.text.toString())){
            txt_userprofile_confirmnewpassword.error = "Please confirm your password"
            txt_userprofile_confirmnewpassword.requestFocus()
            return
        }

        val user : FirebaseUser? = auth.currentUser
        if(user != null && user.email != null){
            // Get auth credentials from the user for re-authentication. The example below shows
            // email and password credentials but there are multiple possible providers,
            // such as GoogleAuthProvider or FacebookAuthProvider.
            val credential = EmailAuthProvider.getCredential(user.email!!, txt_userprofile_oldpassword.text.toString())

            // Prompt the user to re-provide their sign-in credentials
            user.reauthenticate(credential).addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(context, "Re-Authentication succeeded.", Toast.LENGTH_SHORT).show()
                    user!!.updatePassword(txt_userprofile_newpassword.text.toString())
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(context, "Password Changed Successfully",
                                            Toast.LENGTH_SHORT).show()
                                    auth.signOut()
//                                    view?.findNavController()?.navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
                                    val intent = Intent(context, LoginActivity::class.java)
                                    startActivity(intent)
                                }
                            }
                }
                else{
                    Toast.makeText(context, "Re-Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                }
            }
        }
//        else {
//            view?.findNavController()?.navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//        }
    }
}