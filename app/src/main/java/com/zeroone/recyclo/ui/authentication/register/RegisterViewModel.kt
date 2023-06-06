package com.zeroone.recyclo.ui.authentication.register


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.zeroone.recyclo.api.ApiConfig
import com.zeroone.recyclo.api.response.ResponseLogin
import com.zeroone.recyclo.api.response.ResponseRegister
import com.zeroone.recyclo.model.SessionPreference
import com.zeroone.recyclo.utils.Event
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val pref: SessionPreference) : ViewModel()  {


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

    fun register(fullname:String,email:String,address:String,phoneNumber:String,city:String,password:String,) {
        _isLoading.value = true
        val jsonObject = JSONObject()
        jsonObject.put("fullname", fullname)
        jsonObject.put("email", email)
        jsonObject.put("address", address)
        jsonObject.put("phoneNumber", phoneNumber)
        jsonObject.put("city", city)
        jsonObject.put("password", password)
        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        var client = ApiConfig.getApiService().register(requestBody)
        client.enqueue(object: Callback<ResponseRegister> {
            override fun onResponse(
                call: Call<ResponseRegister>,
                response: Response<ResponseRegister>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    val token = response.body()!!.token
                    setToken(token)
                    _status.value = true
                    return
                }
            }

            override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                _isLoading.value = false
                _snackbarText.value = Event(t.message.toString())
            }

        })


    }
}