package com.zeroone.recyclo.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.zeroone.recyclo.api.ApiConfig
import com.zeroone.recyclo.api.response.DataItem
import com.zeroone.recyclo.api.response.DataItemCart
import com.zeroone.recyclo.api.response.ResponseAddTransaction
import com.zeroone.recyclo.api.response.ResponseCarts
import com.zeroone.recyclo.api.response.ResponseDeleteCart
import com.zeroone.recyclo.api.response.ResponseOpCart
import com.zeroone.recyclo.model.SessionPreference
import com.zeroone.recyclo.utils.Event
import kotlinx.coroutines.launch
import okhttp3.internal.notify
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CartViewModel(private val pref: SessionPreference) : ViewModel()  {


    private val _goods = MutableLiveData<List<DataItemCart>>()
    val goods: LiveData<List<DataItemCart>> = _goods

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _status2 = MutableLiveData<Boolean>()
    val status2: LiveData<Boolean> = _status2

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

    fun getAllCarts(token:String) {
        _isLoading.value = true


        var client = ApiConfig.getApiService().getAllCarts("Bearer $token")
        client.enqueue(object: Callback<ResponseCarts> {
            override fun onResponse(
                call: Call<ResponseCarts>,
                response: Response<ResponseCarts>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    try {

                        _goods.value = response.body()!!.data
                        _status.value = true
                        return
                    } catch (e : Exception) {

                    }
                }
                _isLoading.value = false
                _snackbarText.value = Event(response.message())

            }

            override fun onFailure(call: Call<ResponseCarts>, t: Throwable) {
                _isLoading.value = false
                _snackbarText.value = Event(t.message.toString())
            }

        })


    }
    fun makeTransaction(token:String) {
        _isLoading.value = true


        var client = ApiConfig.getApiService().makeTransaction("Bearer $token")
        client.enqueue(object: Callback<ResponseAddTransaction> {
            override fun onResponse(
                call: Call<ResponseAddTransaction>,
                response: Response<ResponseAddTransaction>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                        _status2.value = true
                        return
                }
                _isLoading.value = false
                _snackbarText.value = Event(response.message())

            }

            override fun onFailure(call: Call<ResponseAddTransaction>, t: Throwable) {
                _isLoading.value = false
                _snackbarText.value = Event(t.message.toString())
            }

        })


    }
    fun deleteCart(token:String,productId : String) {
        _isLoading.value = true


        var client = ApiConfig.getApiService().deleteCart("Bearer $token",productId)
        client.enqueue(object: Callback<ResponseDeleteCart> {
            override fun onResponse(
                call: Call<ResponseDeleteCart>,
                response: Response<ResponseDeleteCart>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    try {
                        _status.value = true
                        return
                    } catch (e : Exception) {

                    }
                }
                _isLoading.value = false
                _snackbarText.value = Event(response.message())

            }

            override fun onFailure(call: Call<ResponseDeleteCart>, t: Throwable) {
                _isLoading.value = false
                _snackbarText.value = Event(t.message.toString())
            }

        })


    }
    fun minCart(token:String,productId : String) {
        _isLoading.value = true


        var client = ApiConfig.getApiService().minCart("Bearer $token",productId)
        client.enqueue(object: Callback<ResponseOpCart> {
            override fun onResponse(
                call: Call<ResponseOpCart>,
                response: Response<ResponseOpCart>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    try {

                        _status.value = true
                        return
                    } catch (e : Exception) {

                    }
                }
                _isLoading.value = false
                _snackbarText.value = Event(response.message())

            }

            override fun onFailure(call: Call<ResponseOpCart>, t: Throwable) {
                _isLoading.value = false
                _snackbarText.value = Event(t.message.toString())
            }

        })


    }
    fun plusCart(token:String,productId : String) {
        _isLoading.value = true


        var client = ApiConfig.getApiService().plusCart("Bearer $token",productId)
        client.enqueue(object: Callback<ResponseOpCart> {
            override fun onResponse(
                call: Call<ResponseOpCart>,
                response: Response<ResponseOpCart>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    try {

                        _status.value = true
                        return
                    } catch (e : Exception) {

                    }
                }
                _isLoading.value = false
                _snackbarText.value = Event(response.message())

            }

            override fun onFailure(call: Call<ResponseOpCart>, t: Throwable) {
                _isLoading.value = false
                _snackbarText.value = Event(t.message.toString())
            }

        })


    }
}