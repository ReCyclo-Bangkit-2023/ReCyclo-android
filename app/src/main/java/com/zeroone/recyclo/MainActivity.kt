package com.zeroone.recyclo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.asLiveData
import com.zeroone.recyclo.model.SessionPreference
import com.zeroone.recyclo.ui.authentication.login.LoginActivity
import com.zeroone.recyclo.ui.home.HomeActivity
import com.zeroone.recyclo.ui.map.MapsActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()

        val token = dataStore.data.map { preferences ->
            preferences[stringPreferencesKey("token")] ?: ""
        }.asLiveData()

        Handler().postDelayed({

            finish()
            token.observe(this){
                if (it.equals("")){
                    startActivity(Intent(this@MainActivity,LoginActivity::class.java))
                } else {
                    startActivity(Intent(this@MainActivity,HomeActivity::class.java))

                }
            }
        }, 2000)

    }


}