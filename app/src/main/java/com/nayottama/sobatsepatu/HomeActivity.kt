package com.nayottama.sobatsepatu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.nayottama.sobatsepatu.databinding.ActivityHomeBinding
import com.nayottama.sobatsepatu.databinding.ActivityRegisterBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityHomeBinding
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        orderWash()
        orderRepair()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    private fun orderWash() {
        binding.btnWash.setOnClickListener {
            val intent = Intent(this, OrderActivity::class.java)
            intent.putExtra(OrderActivity.EXTRA_JENIS, "Wash")
            startActivity(intent)
        }
    }

    private fun orderRepair() {
        binding.btnRepair.setOnClickListener {
            val intent = Intent(this, OrderActivity::class.java)
            intent.putExtra(OrderActivity.EXTRA_JENIS, "Repair")
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu1 -> {
                val i = Intent(this, AccountActivity::class.java)
                startActivity(i)
                true
            }
            else -> true
        }
    }
}