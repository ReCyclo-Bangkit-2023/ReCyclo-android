package com.zeroone.recyclo.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zeroone.recyclo.R
import com.zeroone.recyclo.databinding.ActivityHomeBinding
import com.zeroone.recyclo.databinding.ActivityLoginBinding
import com.zeroone.recyclo.ui.longlist.LonglistActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        supportActionBar?.hide()

        binding.waste.setOnClickListener {
            startActivity(Intent(this@HomeActivity,LonglistActivity::class.java))
        }

    }
}