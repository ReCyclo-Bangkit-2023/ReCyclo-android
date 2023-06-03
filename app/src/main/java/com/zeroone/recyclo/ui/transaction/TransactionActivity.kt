package com.zeroone.recyclo.ui.transaction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zeroone.recyclo.R
import com.zeroone.recyclo.databinding.ActivityTransactionBinding

class TransactionActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTransactionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

    }
}