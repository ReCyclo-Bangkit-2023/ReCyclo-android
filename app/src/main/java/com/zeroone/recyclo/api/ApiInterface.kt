package com.zeroone.recyclo.api

import com.zeroone.recyclo.api.response.DataItem
import com.zeroone.recyclo.api.response.ResponseGoods
import com.zeroone.recyclo.api.response.ResponseLogin
import com.zeroone.recyclo.api.response.ResponseRegister
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {
    @POST("login")
    fun login(@Body requestBody: RequestBody): Call<ResponseLogin>

    @POST("register")
    fun register(@Body requestBody: RequestBody): Call<ResponseRegister>

    @GET("api/recycled-goods")
    fun goods(@Header("Authorization") token: String): Call<ResponseGoods>

    @GET("api/recycled-goods")
    suspend fun paggingGoods(@Header("Authorization") token: String, @Query("page") page: Int,
                             @Query("size") size: Int): Response<ResponseGoods>

    @POST("api/recycled-goods")
    fun addGoods(@Body requestBody: RequestBody): Call<ResponseGoods>
}
