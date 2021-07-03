package com.nayottama.sobatsepatu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.nayottama.sobatsepatu.databinding.ActivityLoginBinding
import com.nayottama.sobatsepatu.databinding.ActivityRegisterBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityLoginBinding
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        hideKeyboard()
        login()
        goToRegis()
    }

    private fun login() {
        binding.btnLogin.setOnClickListener {
            if(binding.edtEmail.text.toString().isEmpty()){
                binding.edtEmail.setError("Field ini harus diisi!!")
            } else if(binding.edtPass.text.toString().isEmpty()){
                binding.edtPass.setError("Field ini harus diisi!!")
            } else {
                auth = Firebase.auth
                val email = binding.edtEmail.text.toString()
                val pass = binding.edtPass.text.toString()
                auth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }

            }
            }

    }

    private fun goToRegis() {
        binding.txtRegis.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun hideKeyboard() {
        binding.layoutLogin.setOnClickListener {
            val view = this?.currentFocus
            if(view != null) {
                val hide = this?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                hide.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }
}