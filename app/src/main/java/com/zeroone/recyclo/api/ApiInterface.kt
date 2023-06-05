package com.zeroone.recyclo.api

import com.zeroone.recyclo.api.response.ResponseLogin
import com.zeroone.recyclo.api.response.ResponseRegister
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST("login")
    fun login(@Body requestBody: RequestBody): Call<ResponseLogin>

    @POST("register")
    fun register(@Body requestBody: RequestBody): Call<ResponseRegister>
}