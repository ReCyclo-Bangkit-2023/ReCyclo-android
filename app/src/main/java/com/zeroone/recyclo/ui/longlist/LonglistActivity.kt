package com.zeroone.recyclo.ui.longlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zeroone.recyclo.R
import com.zeroone.recyclo.databinding.ActivityLonglistBinding
import com.zeroone.recyclo.ui.detail.DetailActivity

class LonglistActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLonglistBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLonglistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.bestSellingProduct1.setOnClickListener {
            startActivity(Intent(this@LonglistActivity,DetailActivity::class.java))
        }



    }
}