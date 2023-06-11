package com.zeroone.recyclo.ui.dashboard.goods.edit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.zeroone.recyclo.R
import com.zeroone.recyclo.api.response.DataItem
import com.zeroone.recyclo.dataStore
import com.zeroone.recyclo.databinding.ActivityEditGoodsBinding
import com.zeroone.recyclo.model.SessionPreference
import com.zeroone.recyclo.ui.dashboard.goods.GoodsViewModel
import com.zeroone.recyclo.ui.dashboard.goods.ViewModelFactory
import com.zeroone.recyclo.utils.LoadingBar

class EditActivity : AppCompatActivity() {
    lateinit var binding : ActivityEditGoodsBinding
    lateinit var vm : GoodsViewModel
    lateinit var loading : LoadingBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditGoodsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val goods= intent.getParcelableExtra<DataItem>("goods")
        val pref = SessionPreference.getInstance(dataStore)
        vm = ViewModelProvider(this, ViewModelFactory(pref)).get(GoodsViewModel::class.java)
        loading = LoadingBar(this)
        binding.inpName.setText(goods?.title)
        binding.inpPrice.setText(goods?.price)
        binding.inpStok.setText(goods?.amount)

        var i = 0
        val typeOfProducts = resources.getStringArray(R.array.KindspinnerItems)
        var id = 0
        for(s : String in typeOfProducts){
            if (s.equals(goods?.kind)) {
                id = i
            }
            i++
        }

        val adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item, typeOfProducts)

        binding.inpJenis.adapter = adapter

        binding.inpJenis.setSelection(id)
        Glide.with(this)
            .load(goods?.image1)
            .centerCrop()
            .into(binding.img1View)

        Glide.with(this)
            .load(goods?.image2)
            .centerCrop()
            .into(binding.img2View)

        Glide.with(this)
            .load(goods?.image3)
            .centerCrop()
            .into(binding.img3View)

        binding.inpDesc.setText(goods?.desc)



    }
}