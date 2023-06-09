package com.zeroone.recyclo.ui.dashboard


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zeroone.recyclo.api.ApiConfig
import com.zeroone.recyclo.api.response.DataItem
import com.zeroone.recyclo.api.response.ResponseGoods
import com.zeroone.recyclo.model.SessionPreference
import com.zeroone.recyclo.repository.GoodsRepository
import com.zeroone.recyclo.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardViewModel(goodsRepository: GoodsRepository, private val pref: SessionPreference) : ViewModel()  {

    val getAllGoods: LiveData<PagingData<DataItem>> = goodsRepository.getGoods().cachedIn(viewModelScope)


    private val _goods = MutableLiveData<List<DataItem>>()
    val goods: LiveData<List<DataItem>> = _goods

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText


    fun getToken(): LiveData<String> {
        return pref.getToken().asLiveData()
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