package com.patel.lolapplication.activitis

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.patel.lolapplication.R
import com.patel.lolapplication.databinding.ActivitySignUp2Binding

class SignUpActivity : AppCompatActivity() {
    var password : String? = null
    val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
    private var binding: ActivitySignUp2Binding? = null
    private lateinit var auth : FirebaseAuth
    private lateinit var databaseRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up2)
        init()
    }
    fun init() {
        removerror()
        auth = FirebaseAuth.getInstance()
        binding!!.btnSignUp.setOnClickListener {
            validaton()
        }
        binding!!.btnLogin.setOnClickListener {
            val changePage = Intent(this, SignInActivity::class.java)
            startActivity(changePage)
        }
    }
    fun registerUser(userName:String,email:String,password:String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    var user : FirebaseUser = auth.currentUser!!
                    var userId:String = user.uid
                    databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(userId)
                    var hashMap:HashMap<String,String> = HashMap()
                    Toast.makeText(this,"SUCCESFULLY REGISTER USER ",Toast.LENGTH_LONG).show()
                    hashMap.put("User Id",userId)
                    hashMap.put("User Name", userName)
                    hashMap.put("User Image","")

                    databaseRef.setValue(hashMap)
                        .addOnCanceledListener {
                            if (it.isSuccessful) {
//                                NOW OPEN NEW ACTIVITY
                            }
                        }
                }
            }
    }

    fun validaton() {
        var error = true
        if (binding!!.etName.text?.isEmpty() == true) {
            binding!!.etName.error = "It is required*"
            error = false
        }
        if (binding!!.etEmail.text?.isEmpty() == true) {
            binding!!.etEmail.error = "It is required*"
            error = false
        }
        if (binding!!.etPassword.text?.isEmpty() == true) {
            binding!!.etPassword.error = "It is required*"
            error = false
        }
        if (binding!!.etConfirmPassword.text?.isEmpty() == true) {
            binding!!.etConfirmPassword.error = "It is required*"
            error = false
        }
        if (error) {
            registerUser(
                binding!!.etName.text.toString(), binding!!.etEmail.text.toString(),
                binding!!.etPassword.text.toString()
            )
            binding!!.etName.text = null
            binding!!.etEmail.text = null
            binding!!.etPassword.text = null
            binding!!.etConfirmPassword.text = null
        }
    }


    fun removerror() {
        binding!!.etName.addTextChangedListener(object : TextWatcher  {
            override fun afterTextChanged(s: Editable) {
                binding!!.etName.error =  null
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
        })
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
                password = binding!!.etPassword.text.toString()
                binding!!.etPassword.error = null
                if (binding!!.etPassword.text?.matches(PASSWORD_PATTERN.toRegex()) == false) {
                    binding!!.etPassword.error = "Please Enter Valid Password"
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
        })
        binding!!.etConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                binding!!.etConfirmPassword.error = null
                if (binding!!.etConfirmPassword.text?.matches(password!!.toRegex()) == false) {
                    binding!!.etConfirmPassword.error = "Do not match password"
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
        })
    }
}