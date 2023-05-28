package com.zeroone.recyclo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.zeroone.recyclo.ui.authentication.login.LoginActivity
import com.zeroone.recyclo.ui.map.MapsActivity
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()


        Handler().postDelayed({
            finish()
            startActivity(Intent(this@MainActivity,LoginActivity::class.java))
        }, 2000)

    }
}