package com.nayottama.sobatsepatu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.nayottama.sobatsepatu.adapter.ListOrderAdapter
import com.nayottama.sobatsepatu.databinding.ActivityAccountBinding
import com.nayottama.sobatsepatu.databinding.ActivityOrderBinding
import com.nayottama.sobatsepatu.helper.Api
import com.nayottama.sobatsepatu.model.Order
import com.nayottama.sobatsepatu.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var _binding: ActivityAccountBinding
    private val binding get() = _binding!!
    private lateinit var nama : String
    private lateinit var email : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAccountBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        getDetailUser()
        getListOrder()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu2, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu1 -> {
                auth.signOut()

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                true
            }
            else -> true
        }
    }

    private fun getDetailUser() {
        auth = Firebase.auth
        val uid = auth.currentUser?.uid

        Api.apiCall().getDetailUser(uid.toString()).enqueue(object :
            Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                Log.d("response-getdata", response.body().toString())
                val user = response.body()
                user.let {
                    nama = user?.nama.toString()
                    email = user?.email.toString()
                }

                binding.txtEmail.text = email
                binding.txtNama.text = nama
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG ).show()
                Log.d("response", "err store db, ${t.message}")
            }
        })
    }

    private fun getListOrder() {
        auth = Firebase.auth
        val uid = auth.currentUser?.uid

        Api.apiCall().getListOrder(uid.toString()).enqueue(object :
            Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                Log.d("response-getdata", response.body().toString())
                val order = response.body()
                order?.let {
                    showRecyclerList(it)
                }

            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG ).show()
                Log.d("response", "err store db, ${t.message}")
            }
        })
    }

    private fun showRecyclerList(order: List<Order>) {
        binding.rvOrder.layoutManager = LinearLayoutManager(this)
        val listOrderAdapter = ListOrderAdapter(order)
        binding.rvOrder.adapter = listOrderAdapter

    }
}