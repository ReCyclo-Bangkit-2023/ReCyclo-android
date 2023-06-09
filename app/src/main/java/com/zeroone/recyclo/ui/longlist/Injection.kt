package com.zeroone.recyclo.ui.longlist

import android.content.Context
import com.zeroone.recyclo.api.ApiConfig
import com.zeroone.recyclo.model.SessionPreference
import com.zeroone.recyclo.repository.ProductRepository

class Injection {
    companion object{
        fun provideRepository(context: Context, sessionPreference: SessionPreference) : ProductRepository{
            val apiService = ApiConfig.getApiService()
            return ProductRepository(apiService, sessionPreference)
        }
    }
}