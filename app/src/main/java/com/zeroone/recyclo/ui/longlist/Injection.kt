package com.zeroone.recyclo.ui.longlist

import android.content.Context
import com.zeroone.recyclo.api.ApiConfig
import com.zeroone.recyclo.model.SessionPreference
import com.zeroone.recyclo.repository.GoodsRepository

class Injection {
    companion object{
        fun provideRepository(context: Context, sessionPreference: SessionPreference) : GoodsRepository{
            val apiService = ApiConfig.getApiService()
            return GoodsRepository(apiService, sessionPreference)
        }
    }
}