package com.zeroone.recyclo.ui.cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zeroone.recyclo.R
import com.zeroone.recyclo.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

    }
}