package com.patel.lolapplication.activitis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.patel.lolapplication.R
import com.patel.lolapplication.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {
    private var binding: ActivitySignInBinding? = null
    private lateinit var auth : FirebaseAuth
    val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        init()
    }
    fun init() {
        auth = FirebaseAuth.getInstance()
        removerror()
        binding!!.btnLogin.setOnClickListener {
            validaton()
        }
        binding!!.btnSignUp.setOnClickListener {
            val changePage = Intent(this, SignUpActivity::class.java)
            startActivity(changePage)
        }
    }
    fun removerror() {
        binding!!.etEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                binding!!.etEmail.error = null
                if (binding!!.etEmail.text?.matches(EMAIL_REGEX.toRegex()) == false) {
                    binding!!.etEmail.error = "Please Enter Valid Email"
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
        })
        binding!!.etPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                binding!!.etPassword.error = null
                if (binding!!.etPassword.text?.matches(PASSWORD_PATTERN.toRegex()) == false) {
                    binding!!.etPassword.error = "Please Enter Valid Password"
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
        })
    }
    fun validaton() {
        var error = true
        if (binding!!.etEmail.text?.isEmpty() == true) {
            binding!!.etEmail.error = "It is required*"
            error = false
        }
        if (binding!!.etPassword.text?.isEmpty() == true) {
            binding!!.etPassword.error = "It is required*"
            error = false
        }
        if (error) {
            loginUser(
                binding!!.etEmail.text.toString(),
                binding!!.etPassword.text.toString()
            )
            binding!!.etEmail.text = null
            binding!!.etPassword.text = null
        }
    }
    fun loginUser(email: String,password:String) {
            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Handle successful login
                            Toast.makeText(this, "Log In Done, Welcome...", Toast.LENGTH_LONG).show()
                        } else {
                            // Handle login failure
                            Toast.makeText(this, "Log In Failed, Try Again...", Toast.LENGTH_LONG).show()
                            Log.e("LoginError", "Login failed: ${task.exception?.message}")
                        }
                    }
            } else {
                // Handle the case where email or password is empty
                Toast.makeText(this, "Email and password cannot be empty", Toast.LENGTH_LONG).show()
            }

//            .addOnCompleteListener(this) { task->
//                if (task.isSuccessful) {
//                    Toast.makeText(this,"Log In Done, Welcome...",Toast.LENGTH_LONG).show()
//                    Log.d("DONE","TASK IS DONE")
////                        val changePage = Intent(this, SignUpActivity::class.java)
////                        startActivity(changePage)
//                } else {
//                    Toast.makeText(this,"Log In Failed, Try Again...",Toast.LENGTH_LONG).show()
//                    Log.d("DONE","TASK IS NOT DONE")
//                }
//            }
//            .addOnCanceledListener(this) {
//                Toast.makeText(this,"Log In Failed, Try Again...",Toast.LENGTH_LONG).show()
//                Log.d("DONE","TASK IS NOT DONE")
//            }
    }
}