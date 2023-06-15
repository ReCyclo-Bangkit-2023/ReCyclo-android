package com.zeroone.recyclo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeroone.recyclo.model.SessionPreference
import com.zeroone.recyclo.utils.Event
import kotlinx.coroutines.launch

class HomeViewModel(private val pref: SessionPreference) : ViewModel()  {


    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText


    fun clearToken(){
        viewModelScope.launch {
            pref.clearToken()
        }
    }




}