package com.zeroone.recyclo.ui.authentication.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zeroone.recyclo.MainActivity
import com.zeroone.recyclo.R
import com.zeroone.recyclo.databinding.ActivityLoginBinding
import com.zeroone.recyclo.ui.authentication.register.RegisterActivity
import com.zeroone.recyclo.ui.home.HomeActivity
import com.zeroone.recyclo.ui.map.MapsActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.registerBtn.setOnClickListener{
            startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
        }

        binding.appCompatButton.setOnClickListener{
            startActivity(Intent(this@LoginActivity,HomeActivity::class.java))
        }
    }
}