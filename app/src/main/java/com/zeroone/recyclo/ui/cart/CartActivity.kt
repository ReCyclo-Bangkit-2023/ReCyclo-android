package com.zeroone.recyclo.ui.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.zeroone.recyclo.api.response.DataItem
import com.zeroone.recyclo.api.response.DataItemCart
import com.zeroone.recyclo.dataStore
import com.zeroone.recyclo.databinding.ActivityCartBinding
import com.zeroone.recyclo.model.SessionPreference
import com.zeroone.recyclo.ui.longlist.LongListAdapter
import com.zeroone.recyclo.ui.transaction.TransactionActivity
import com.zeroone.recyclo.utils.LoadingBar
import com.zeroone.recyclo.utils.SpacesItemDecoration

class CartActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCartBinding
    private lateinit var vm : CartViewModel
    private lateinit var loading : LoadingBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val pref = SessionPreference.getInstance(dataStore)
        vm = ViewModelProvider(this, ViewModelFactory(pref)).get(CartViewModel::class.java)
        loading = LoadingBar(this)

        val layoutManager = LinearLayoutManager(this)
        binding.rvCart.layoutManager = layoutManager
        binding.rvCart.addItemDecoration(SpacesItemDecoration(20))

        binding.include.back.setOnClickListener {
            finish()
        }

        binding.addTransaction.setOnClickListener {
            vm.getToken().observe(this){
                vm.makeTransaction(it)
            }

            vm.status.observe(this){
                finish()
                startActivity(Intent(this@CartActivity,TransactionActivity::class.java))
            }

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


        vm.getToken().observe(this){
            vm.getAllCarts(it)
        }

        vm.goods.observe(this){
            setGoods(it)
        }




    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            loading.startLoading()
        } else {
            loading.isDismiss()
        }
    }

    private fun setGoods(data:List<DataItemCart>) {
        var  arrGoods:ArrayList<DataItemCart> =  ArrayList()
        for (goods in data) {
            arrGoods.add(goods)
        }
        val adapter = CartAdapter(vm,arrGoods,this,lifecycle)
        binding.rvCart.adapter = adapter

    }
}