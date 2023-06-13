package com.zeroone.recyclo.ui.transaction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zeroone.recyclo.R
import com.zeroone.recyclo.databinding.ActivityTransactionBinding
import com.zeroone.recyclo.ui.cart.CartActivity
import com.zeroone.recyclo.ui.dashboard.DashboardActivity
import com.zeroone.recyclo.ui.home.HomeActivity

class TransactionActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTransactionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.bottom.cartMenu.setOnClickListener{
            startActivity(Intent(this@TransactionActivity, CartActivity::class.java))
        }

        binding.bottom.dashboardMenu.setOnClickListener{
            startActivity(Intent(this@TransactionActivity, DashboardActivity::class.java))
        }

        binding.bottom.transactionMenu.setOnClickListener{
            startActivity(Intent(this@TransactionActivity,TransactionActivity::class.java))
        }

        binding.bottom.homeMenu.setOnClickListener{
            startActivity(Intent(this@TransactionActivity,HomeActivity::class.java))
        }
    }
}