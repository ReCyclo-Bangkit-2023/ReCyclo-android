package com.zeroone.recyclo.ui.longlist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zeroone.recyclo.model.SessionPreference

class ViewModelFactory(private val context: Context, private val sessionPreference: SessionPreference) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LonglistViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LonglistViewModel(sessionPreference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}