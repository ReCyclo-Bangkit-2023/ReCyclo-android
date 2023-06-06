package com.zeroone.recyclo.ui.authentication.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.zeroone.recyclo.dataStore
import com.zeroone.recyclo.databinding.ActivityRegisterBinding
import com.zeroone.recyclo.model.SessionPreference
import com.zeroone.recyclo.ui.authentication.register.ViewModelFactory
import com.zeroone.recyclo.ui.home.HomeActivity
import com.zeroone.recyclo.utils.LoadingBar

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    private lateinit var vm : RegisterViewModel
    private lateinit var loading : LoadingBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val pref = SessionPreference.getInstance(dataStore)
        vm = ViewModelProvider(this, ViewModelFactory(pref)).get(
            RegisterViewModel::class.java
        )


        loading = LoadingBar(this)
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
                startActivity(Intent(this@RegisterActivity, HomeActivity::class.java))
            }
        }



        binding.register.setOnClickListener {
            vm.register(binding.registerName.text.toString(),binding.registerEmail.text.toString(),binding.registerAddress.text.toString(),binding.registerPhone.text.toString(),binding.registerCity.text.toString(),binding.registerPassword.text.toString())
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