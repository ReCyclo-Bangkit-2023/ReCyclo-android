package com.zeroone.recyclo.ui.transaction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.zeroone.recyclo.api.response.DataItemTransactionAll
import com.zeroone.recyclo.dataStore
import com.zeroone.recyclo.databinding.ActivityTransactionBinding
import com.zeroone.recyclo.model.SessionPreference
import com.zeroone.recyclo.ui.cart.CartActivity
import com.zeroone.recyclo.ui.dashboard.DashboardActivity
import com.zeroone.recyclo.ui.home.HomeActivity
import com.zeroone.recyclo.utils.LoadingBar
import com.zeroone.recyclo.utils.SpacesItemDecoration

class TransactionActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTransactionBinding
    private lateinit var vm : TransactionViewModel
    private lateinit var loading : LoadingBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        loading = LoadingBar(this)
        val pref = SessionPreference.getInstance(dataStore)
        vm = ViewModelProvider(this,ViewModelFactory(pref)).get(TransactionViewModel::class.java)

        binding.bottom.cartMenu.setOnClickListener{
            finish()
            startActivity(Intent(this@TransactionActivity, CartActivity::class.java))
        }

        binding.bottom.dashboardMenu.setOnClickListener{
            finish()
            startActivity(Intent(this@TransactionActivity, DashboardActivity::class.java))
        }

        binding.bottom.transactionMenu.setOnClickListener{
            finish()
            startActivity(Intent(this@TransactionActivity, TransactionActivity::class.java))
        }

        binding.bottom.homeMenu.setOnClickListener{
            finish()
            startActivity(Intent(this@TransactionActivity, HomeActivity::class.java))
        }

        vm.getToken().observe(this){
            vm.getAllTransaction(it)
        }

        vm.transaction.observe(this){
            setTransaction(it)
        }
        vm.isLoading.observe(this) {
            showLoading(it)
        }

        vm.snackbarText.observe(this) {
            it.getContentIfNotHandled()?.let { it1 ->
                Snackbar.make(
                    window.decorView.rootView,
                    it1,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvTransaction.layoutManager = layoutManager
        binding.rvTransaction.addItemDecoration(SpacesItemDecoration(20))

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            loading.startLoading()
        } else {
            loading.isDismiss()
        }
    }

    private fun setTransaction(data:List<DataItemTransactionAll>) {
        var  arrGoods:ArrayList<DataItemTransactionAll> =  ArrayList()
        for (goods in data) {
            arrGoods.add(goods)
        }
        val adapter = TransactionAdapter(vm,arrGoods,this,lifecycle)
        binding.rvTransaction.adapter = adapter

    }
}