package com.zeroone.recyclo.ui.authentication.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})";
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

        binding.registerEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if(!s.toString().isNullOrEmpty()){
                    if (!s.toString().matches(emailRegex.toRegex())){
                        binding.registerEmail.setError( "email tidak sesuai format")
                        return
                    }
                }else{
                    binding.registerEmail.setError( "email tidak boleh kosong")
                    return
                }
            }

        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            loading.startLoading()
        } else {
            loading.isDismiss()
        }
    }
}