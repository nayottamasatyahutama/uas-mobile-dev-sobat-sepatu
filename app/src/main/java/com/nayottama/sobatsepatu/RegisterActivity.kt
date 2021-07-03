package com.nayottama.sobatsepatu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.nayottama.sobatsepatu.databinding.ActivityLoginBinding
import com.nayottama.sobatsepatu.databinding.ActivityRegisterBinding
import com.nayottama.sobatsepatu.helper.Api
import com.nayottama.sobatsepatu.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityRegisterBinding
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        hideKeyboard()
        goToLogin()
        register()

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun register() {
        auth = Firebase.auth
        binding.btnRegister.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val pass = binding.edtPass.text.toString()
            val nama = binding.edtName.text.toString()

            if(binding.edtEmail.text.toString().isEmpty()){
                binding.edtEmail.setError("Field ini harus diisi!!")
            } else if(binding.edtName.text.toString().isEmpty()){
                binding.edtName.setError("Field ini harus diisi!!")
            } else if(binding.edtPass.text.toString().isEmpty()){
                binding.edtPass.setError("Field ini harus diisi!!")
            } else if(binding.edtPass.text.length < 8) {
                binding.edtPass.setError("Password minimal memiliki panjang 8 karakter!!")
            } else {
                auth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val uid = auth.currentUser?.uid
                            val user = User(nama, email, uid!!)
                            Api.apiCall().createUser(user).enqueue(object :
                                Callback<User> {
                                override fun onResponse(call: Call<User>, response: Response<User>) {
                                    Log.d("response", "sukses register dan store data user ke db")
                                    val intent = Intent(applicationContext, HomeActivity::class.java)
                                    startActivity(intent)
                                }

                                override fun onFailure(call: Call<User>, t: Throwable) {
                                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG ).show()
                                    Log.d("response", "err store db, ${t.message}")
                                }
                            })


                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            }


        }


    }


    private fun goToLogin() {
        binding.txtLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun hideKeyboard() {
        binding.layoutRegister.setOnClickListener {
            val view = this?.currentFocus
            if(view != null) {
                val hide = this?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                hide.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }
}