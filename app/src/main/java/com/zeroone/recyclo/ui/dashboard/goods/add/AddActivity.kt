package com.zeroone.recyclo.ui.dashboard.goods.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zeroone.recyclo.R
import com.zeroone.recyclo.databinding.ActivityAddGoodsBinding

class AddActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddGoodsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddGoodsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.img1.setOnClickListener {

        }

    }
}