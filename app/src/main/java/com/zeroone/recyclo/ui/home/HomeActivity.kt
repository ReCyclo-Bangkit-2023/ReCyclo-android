package com.zeroone.recyclo.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zeroone.recyclo.R

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.hide()
    }
}