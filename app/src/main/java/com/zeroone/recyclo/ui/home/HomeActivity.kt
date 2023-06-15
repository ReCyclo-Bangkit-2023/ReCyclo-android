package com.zeroone.recyclo.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.zeroone.recyclo.R
import com.zeroone.recyclo.dataStore
import com.zeroone.recyclo.databinding.ActivityHomeBinding
import com.zeroone.recyclo.databinding.ActivityLoginBinding
import com.zeroone.recyclo.model.SessionPreference
import com.zeroone.recyclo.ui.authentication.login.LoginActivity
import com.zeroone.recyclo.ui.cart.CartActivity
import com.zeroone.recyclo.ui.dashboard.DashboardActivity
import com.zeroone.recyclo.ui.longlist.LonglistActivity
import com.zeroone.recyclo.ui.map.MapsActivity
import com.zeroone.recyclo.ui.transaction.TransactionActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding
    private lateinit var vm : HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        supportActionBar?.hide()

        val pref = SessionPreference.getInstance(dataStore)
        vm = ViewModelProvider(this,ViewModelFactory(pref)).get(HomeViewModel::class.java)

        binding.waste.setOnClickListener {
            startActivity(Intent(this@HomeActivity,MapsActivity::class.java))
        }

        binding.goods.setOnClickListener {
            startActivity(Intent(this@HomeActivity,LonglistActivity::class.java))
        }

        binding.bottom.cartMenu.setOnClickListener{
            startActivity(Intent(this@HomeActivity, CartActivity::class.java))
        }

        binding.bottom.dashboardMenu.setOnClickListener{
            startActivity(Intent(this@HomeActivity, DashboardActivity::class.java))
        }

        binding.bottom.transactionMenu.setOnClickListener{
            startActivity(Intent(this@HomeActivity, TransactionActivity::class.java))
        }

        binding.bottom.homeMenu.setOnClickListener{
            startActivity(Intent(this@HomeActivity, HomeActivity::class.java))
        }

        var status = false
        binding.include.profil.setOnClickListener{
            status = !status

            if (status) {
                binding.detailProfil.visibility = View.VISIBLE
            } else {
                binding.detailProfil.visibility = View.GONE

            }
        }

        binding.logout.setOnClickListener {
            vm.clearToken()
            finish()
            startActivity(Intent(this@HomeActivity,LoginActivity::class.java))
        }

    }
}