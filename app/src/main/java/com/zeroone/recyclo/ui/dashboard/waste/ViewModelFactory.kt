package com.zeroone.recyclo.ui.dashboard.waste

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zeroone.recyclo.model.SessionPreference
import com.zeroone.recyclo.ui.dashboard.goods.GoodsViewModel

class ViewModelFactory(private val pref: SessionPreference) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WasteViewModel::class.java)) {
            return WasteViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}