package com.zeroone.recyclo.ui.longlist

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.zeroone.recyclo.api.response.DataItemproduct
import com.zeroone.recyclo.dataStore
import com.zeroone.recyclo.databinding.ActivityLonglistBinding
import com.zeroone.recyclo.model.SessionPreference
import com.zeroone.recyclo.ui.cart.CartActivity
import com.zeroone.recyclo.ui.dashboard.DashboardActivity
import com.zeroone.recyclo.ui.detail.DetailActivity
import com.zeroone.recyclo.ui.home.HomeActivity
import com.zeroone.recyclo.ui.transaction.TransactionActivity
import com.zeroone.recyclo.utils.LoadingBar
import com.zeroone.recyclo.utils.Utils


class LonglistActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLonglistBinding
    private lateinit var vm: LonglistViewModel
    private lateinit var loading: LoadingBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLonglistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


//        binding.titleProduct1.setText()

        val pref = SessionPreference.getInstance(dataStore)
        vm = ViewModelProvider(this,ViewModelFactory(this,pref)).get(LonglistViewModel::class.java)
        loading = LoadingBar(this)
        val layoutManager = GridLayoutManager(this,2)
        binding.rvGoods.layoutManager = layoutManager


        vm.getToken().observe(this){
            vm.getGoods(it)
        }
        vm.goods.observe(this){
            setGoods(it)
            setGoodsForBestSelling(it)
        }

        binding.include.back.setOnClickListener{
            finish()
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

        binding.bottom.cartMenu.setOnClickListener{
            finish()
            startActivity(Intent(this@LonglistActivity, CartActivity::class.java))
        }

        binding.bottom.dashboardMenu.setOnClickListener{
            finish()
            startActivity(Intent(this@LonglistActivity, DashboardActivity::class.java))
        }

        binding.bottom.transactionMenu.setOnClickListener{
            finish()
            startActivity(Intent(this@LonglistActivity, TransactionActivity::class.java))
        }

        binding.bottom.homeMenu.setOnClickListener{
            finish()
            startActivity(Intent(this@LonglistActivity, HomeActivity::class.java))
        }

        binding.searchLonglist.setOnEditorActionListener{ v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(v.text.toString())
            }
            true
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            loading.startLoading()
        } else {
            loading.isDismiss()
        }
    }

    private fun performSearch(string: String) {
        vm.getToken().observe(this){
            vm.getGoodsSearch(it,string)
        }

    }

    private fun setGoods(data:List<DataItemproduct>) {
        var  arrGoods:ArrayList<DataItemproduct> =  ArrayList()
        for (goods in data) {
            arrGoods.add(goods)
        }
        val adapter = LongListAdapter(this,arrGoods)
        binding.rvGoods.adapter = adapter

    }

    private fun setGoodsForBestSelling(data:List<DataItemproduct>) {
        var i = 0
        for (goods in data) {
            if (i == 0) {
                binding.titleProduct1.setText(goods.title)
                Glide.with(this)
                    .load(goods.image1)
                    .centerCrop()
                    .into(binding.heroProduct1)
                binding.priceProduct1.setText(Utils.formatrupiah(goods.price.toDouble()))
                binding.locationProduct1.setText(goods.sellerDetails.city)

                binding.bestSellingProduct1.setOnClickListener {
                    val intent = Intent(this@LonglistActivity,DetailActivity::class.java)
                    intent.putExtra("product",goods)
                    startActivity(intent)
                }
            }else if (i == 1) {
                binding.titleProduct2.setText(goods.title)
                Glide.with(this)
                    .load(goods.image1)
                    .centerCrop()
                    .into(binding.heroProduct2)
                binding.priceProduct2.setText(Utils.formatrupiah(goods.price.toDouble()))
                binding.locationProduct2.setText(goods.sellerDetails.city)
                binding.bestSellingProduct2.setOnClickListener {
                    val intent = Intent(this@LonglistActivity,DetailActivity::class.java)
                    intent.putExtra("product",goods)
                    startActivity(intent)
                }
            } else if (i == 2) {
                binding.titleProduct3.setText(goods.title)
                Glide.with(this)
                    .load(goods.image1)
                    .centerCrop()
                    .into(binding.heroProduct3)
                binding.priceProduct3.setText(Utils.formatrupiah(goods.price.toDouble()))
                binding.locationProduct3.setText(goods.sellerDetails.city)
                binding.bestSellingProduct3.setOnClickListener {
                    val intent = Intent(this@LonglistActivity,DetailActivity::class.java)
                    intent.putExtra("product",goods)
                    startActivity(intent)
                }
            }else if (i == 3) {
                binding.titleProduct4.setText(goods.title)
                Glide.with(this)
                    .load(goods.image1)
                    .centerCrop()
                    .into(binding.heroProduct4)
                binding.priceProduct4.setText(Utils.formatrupiah(goods.price.toDouble()))
                binding.locationProduct4.setText(goods.sellerDetails.city)
                binding.bestSellingProduct4.setOnClickListener {
                    val intent = Intent(this@LonglistActivity,DetailActivity::class.java)
                    intent.putExtra("product",goods)
                    startActivity(intent)
                }
            }

            if (i > 3) { return }


            i++
        }

    }
}