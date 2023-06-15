package com.zeroone.recyclo.ui.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.zeroone.recyclo.api.ApiConfig
import com.zeroone.recyclo.api.response.DataItemTransactionAll
import com.zeroone.recyclo.api.response.ResponseTransaction
import com.zeroone.recyclo.model.SessionPreference
import com.zeroone.recyclo.utils.Event
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionViewModel(private val pref : SessionPreference) : ViewModel(){


    private val _transaction = MutableLiveData<List<DataItemTransactionAll>>()
    val transaction: LiveData<List<DataItemTransactionAll>> = _transaction

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText


    fun getToken(): LiveData<String> {
        return pref.getToken().asLiveData()
    }

    fun setToken(token:String) {
        viewModelScope.launch {
            pref.setToken(token)
        }
    }

    fun getAllTransaction(token:String) {
        _isLoading.value = true


        var client = ApiConfig.getApiService().getAllTransactions("Bearer $token")
        client.enqueue(object: Callback<ResponseTransaction> {
            override fun onResponse(
                call: Call<ResponseTransaction>,
                response: Response<ResponseTransaction>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    try {

                        _transaction.value = response.body()!!.data
                        _status.value = true
                        return
                    } catch (e : Exception) {

                    }
                }
                _isLoading.value = false
                _snackbarText.value = Event(response.message())

            }

            override fun onFailure(call: Call<ResponseTransaction>, t: Throwable) {
                _isLoading.value = false
                _snackbarText.value = Event(t.message.toString())
            }

        })


    }
}