package com.nayottama.sobatsepatu

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.nayottama.sobatsepatu.databinding.ActivityOrderBinding
import com.nayottama.sobatsepatu.helper.Api
import com.nayottama.sobatsepatu.model.Order
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityOrderBinding
    private val binding get() = _binding!!
    private lateinit var jenis : String
    private lateinit var auth: FirebaseAuth

    private val biayaWash : Int = 15000
    private val biayaRepair : Int = 20000

    companion object {
        const val EXTRA_JENIS = "extra_jenis"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOrderBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        jenis = intent.getStringExtra(EXTRA_JENIS).toString()

        binding.txtHarga.text = if(jenis == "Wash") biayaWash.toString() else biayaRepair.toString()

        hideKeyboard()
        order()
    }

    private fun hideKeyboard() {
        binding.layoutOrder.setOnClickListener {
            val view = this?.currentFocus
            if(view != null) {
                val hide = this?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                hide.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }

    private fun order() {
        binding.btnOrder.setOnClickListener {
            auth = Firebase.auth

            if(binding.edtMerk.text.toString().isEmpty()){
                binding.edtMerk.setError("Field ini harus diisi!!")
            } else if(binding.edtCatatan.text.toString().isEmpty()){
                binding.edtCatatan.setError("Field ini harus diisi!!")
            } else {
                val merk = binding.edtMerk.text.toString()
                val catatan = binding.edtCatatan.text.toString()
                val userid = auth.currentUser?.uid
                val biaya = if(jenis == "Wash") biayaWash else biayaRepair

                val order = Order(
                    merk,
                    catatan,
                    userid!!,
                    jenis,
                    biaya
                )

                Api.apiCall().createOrder(order).enqueue(object :
                    Callback<Order> {
                    override fun onResponse(call: Call<Order>, response: Response<Order>) {
                        Log.d("response", "sukses order store data user ke db")
                        binding.txtHarga.text = if(jenis == "Wash") biayaWash.toString() else biayaRepair.toString()
                        binding.edtCatatan.text.clear()
                        binding.edtMerk.text.clear()
                    }

                    override fun onFailure(call: Call<Order>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG ).show()
                        Log.d("response", "err store db, ${t.message}")
                    }
                })
            }


        }
    }
}