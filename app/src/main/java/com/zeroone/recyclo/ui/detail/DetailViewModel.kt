package com.zeroone.recyclo.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.zeroone.recyclo.api.ApiConfig
import com.zeroone.recyclo.api.response.DataItem
import com.zeroone.recyclo.api.response.ResponseAddToCart
import com.zeroone.recyclo.model.SessionPreference
import com.zeroone.recyclo.utils.Event
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val pref: SessionPreference) : ViewModel() {
    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _product = MutableLiveData<List<DataItem>>()
    val product: LiveData<List<DataItem>> = _product

    fun getToken(): LiveData<String> {
        return pref.getToken().asLiveData()
    }

    fun setToken(token:String) {
        viewModelScope.launch {
            pref.setToken(token)
        }
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    fun addToCart(token: String,productId: String){
        _isLoading.value = true

        val jsonObject = JSONObject()
        jsonObject.put("recycledId", productId)
        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        val client = ApiConfig.getApiService().addToCart("Bearer $token",requestBody)
        client.enqueue(object : Callback<ResponseAddToCart> {
            override fun onResponse(
                call: Call<ResponseAddToCart>,
                response: Response<ResponseAddToCart>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _status.value = true
                }
            }

            override fun onFailure(call: Call<ResponseAddToCart>, t: Throwable) {
                _isLoading.value = false
                _snackbarText.value = Event(t.message.toString())
            }

        })
    }
}