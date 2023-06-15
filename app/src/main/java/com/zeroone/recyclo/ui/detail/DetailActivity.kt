package com.zeroone.recyclo.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.smarteist.autoimageslider.SliderView
import com.zeroone.recyclo.R
import com.zeroone.recyclo.api.response.DataItem
import com.zeroone.recyclo.api.response.DataItemproduct
import com.zeroone.recyclo.dataStore
import com.zeroone.recyclo.databinding.ActivityDetailBinding
import com.zeroone.recyclo.model.SessionPreference
import com.zeroone.recyclo.ui.cart.CartActivity
import com.zeroone.recyclo.utils.LoadingBar
import com.zeroone.recyclo.utils.Utils

class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding
    private lateinit var vm : DetailViewModel
    private lateinit var loading : LoadingBar
    lateinit var imageUrl: ArrayList<String>
    lateinit var sliderView: SliderView
    lateinit var sliderAdapter: SliderAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val product= intent.getParcelableExtra<DataItemproduct>("product")

        loading = LoadingBar(this)
        val pref = SessionPreference.getInstance(dataStore)
        vm = ViewModelProvider(this, ViewModelFactory(pref)).get(DetailViewModel::class.java)


        sliderView = findViewById(R.id.imageSlider)
        imageUrl = ArrayList()
        imageUrl =
            (imageUrl + product?.image1) as ArrayList<String>
        imageUrl =
            (imageUrl + product?.image2) as ArrayList<String>
        imageUrl =
            (imageUrl + product?.image3) as ArrayList<String>

        sliderAdapter = SliderAdapter( imageUrl)
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.scrollTimeInSec = 3
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()

        binding.productDetail.setText(product?.title)
        binding.productSeller.setText(product?.sellerDetails?.fullname)
        binding.cityProduct.setText(product?.sellerDetails?.city)
        binding.productDesc.setText(product?.desc)
        binding.productSold.setText(product?.sold.toString())
        binding.productStock.setText(product?.amount)
        binding.productPrice.setText(Utils.formatrupiah(product?.price?.toDouble()))
        binding.include.back.setOnClickListener{
            finish()
        }

        binding.addToCart.setOnClickListener {
            vm.getToken().observe(this){
                vm.addToCart(it,product?.id!!)
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


        vm.status.observe(this){
            if (it) {
                finish()
                startActivity(Intent(this@DetailActivity,CartActivity::class.java))
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            loading.startLoading()
        } else {
            loading.isDismiss()
        }
    }
}