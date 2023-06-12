package com.zeroone.recyclo.ui.dashboard.goods

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.zeroone.recyclo.api.ApiConfig
import com.zeroone.recyclo.api.response.DataItem
import com.zeroone.recyclo.api.response.ResponseAdd
import com.zeroone.recyclo.api.response.ResponseDeleteGoods
import com.zeroone.recyclo.api.response.ResponseGoods
import com.zeroone.recyclo.model.SessionPreference
import com.zeroone.recyclo.utils.Event
import com.zeroone.recyclo.utils.Utils
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class GoodsViewModel(private val pref: SessionPreference) : ViewModel() {

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _goods = MutableLiveData<List<DataItem>>()
    val goods: LiveData<List<DataItem>> = _goods

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

    fun edit(idProduct: String,name : String,price : String,kind : String,amount : String,img1 : File,img2 : File,img3 : File, desc : String, lat : String,long : String, token: String){
        _isLoading.value = true
        val requestImageFile1 = img1.asRequestBody("image/*".toMediaTypeOrNull())
        val requestImageFile2 = img2.asRequestBody("image/*".toMediaTypeOrNull())
        val requestImageFile3 = img3.asRequestBody("image/*".toMediaTypeOrNull())
        val imageMultipart1: MultipartBody.Part = MultipartBody.Part.createFormData("image1", img1.name, requestImageFile1)
        val imageMultipart2: MultipartBody.Part = MultipartBody.Part.createFormData("image2", img2.name, requestImageFile2)
        val imageMultipart3: MultipartBody.Part = MultipartBody.Part.createFormData("image3", img3.name, requestImageFile3)
        val name = name.toRequestBody("text/plain".toMediaType())
        val price = Utils.CurrencyToNumber(price).toString().toRequestBody("text/plain".toMediaType())
        val amount = amount.toRequestBody("text/plain".toMediaType())
        val kind = kind.toRequestBody("text/plain".toMediaType())
        val lat = lat.toRequestBody("text/plain".toMediaType())
        val long = long.toRequestBody("text/plain".toMediaType())
        val desc = desc.toRequestBody("text/plain".toMediaType())

        val client = ApiConfig.getApiService().editGoods(idProduct,"Bearer $token",name,price,kind,imageMultipart1,imageMultipart2,imageMultipart3,desc,lat, long, amount)
        client.enqueue(object : Callback<ResponseAdd> {
            override fun onResponse(
                call: Call<ResponseAdd>,
                response: Response<ResponseAdd>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _status.value = true
                }
            }

            override fun onFailure(call: Call<ResponseAdd>, t: Throwable) {
                _isLoading.value = false
                _snackbarText.value = Event(t.message.toString())
            }

        })
    }

    fun insert(name : String,price : String,kind : String,amount : String,img1 : File,img2 : File,img3 : File, desc : String, lat : String,long : String, token: String){
        _isLoading.value = true
        val requestImageFile1 = img1.asRequestBody("image/*".toMediaTypeOrNull())
        val requestImageFile2 = img2.asRequestBody("image/*".toMediaTypeOrNull())
        val requestImageFile3 = img3.asRequestBody("image/*".toMediaTypeOrNull())
        val imageMultipart1: MultipartBody.Part = MultipartBody.Part.createFormData("image1", img1.name, requestImageFile1)
        val imageMultipart2: MultipartBody.Part = MultipartBody.Part.createFormData("image2", img2.name, requestImageFile2)
        val imageMultipart3: MultipartBody.Part = MultipartBody.Part.createFormData("image3", img3.name, requestImageFile3)
        val name = name.toRequestBody("text/plain".toMediaType())
        val price = Utils.CurrencyToNumber(price).toString().toRequestBody("text/plain".toMediaType())
        val amount = amount.toRequestBody("text/plain".toMediaType())
        val kind = kind.toRequestBody("text/plain".toMediaType())
        val lat = lat.toRequestBody("text/plain".toMediaType())
        val long = long.toRequestBody("text/plain".toMediaType())
        val desc = desc.toRequestBody("text/plain".toMediaType())

        val client = ApiConfig.getApiService().addGoods("Bearer $token",name,price,kind,imageMultipart1,imageMultipart2,imageMultipart3,desc,lat, long, amount)
        client.enqueue(object : Callback<ResponseAdd> {
            override fun onResponse(
                call: Call<ResponseAdd>,
                response: Response<ResponseAdd>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _status.value = true
                }
            }

            override fun onFailure(call: Call<ResponseAdd>, t: Throwable) {
                _isLoading.value = false
                _snackbarText.value = Event(t.message.toString())
            }

        })
    }
    fun delete(token: String,id : String){
        _isLoading.value = true

        val client = ApiConfig.getApiService().deleteGoods("Bearer $token",id)
        client.enqueue(object : Callback<ResponseDeleteGoods> {
            override fun onResponse(
                call: Call<ResponseDeleteGoods>,
                response: Response<ResponseDeleteGoods>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _status.value = true
                }
            }

            override fun onFailure(call: Call<ResponseDeleteGoods>, t: Throwable) {
                _snackbarText.value = Event(t.message.toString())
            }

        })
    }
    fun getGoods(token: String){
        _isLoading.value = true


        var client = ApiConfig.getApiService().goods("Bearer $token")
        client.enqueue(object: Callback<ResponseGoods> {
            override fun onResponse(call: Call<ResponseGoods>, response: Response<ResponseGoods>) {
                _isLoading.value= false
                if (response.isSuccessful) {
                    _goods.value = response.body()?.data
                }
            }

            override fun onFailure(call: Call<ResponseGoods>, t: Throwable) {
                _snackbarText.value = Event(t.message.toString())
            }

        })

    }


}