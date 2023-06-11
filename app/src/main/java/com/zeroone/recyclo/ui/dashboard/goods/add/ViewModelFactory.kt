package com.zeroone.recyclo.ui.dashboard.goods.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zeroone.recyclo.model.SessionPreference

class ViewModelFactory(private val pref: SessionPreference) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddViewModel::class.java)) {
            return AddViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}