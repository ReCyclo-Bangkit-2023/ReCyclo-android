package com.zeroone.recyclo.api

import com.zeroone.recyclo.api.response.ResponseAdd
import com.zeroone.recyclo.api.response.ResponseAddToCart
import com.zeroone.recyclo.api.response.ResponseAddTransaction
import com.zeroone.recyclo.api.response.ResponseCarts
import com.zeroone.recyclo.api.response.ResponseDeleteCart
import com.zeroone.recyclo.api.response.ResponseDeleteGoods
import com.zeroone.recyclo.api.response.ResponseGoods
import com.zeroone.recyclo.api.response.ResponseLogin
import com.zeroone.recyclo.api.response.ResponseOpCart
import com.zeroone.recyclo.api.response.ResponsePrediction
import com.zeroone.recyclo.api.response.ResponseProducts
import com.zeroone.recyclo.api.response.ResponseRegister
import com.zeroone.recyclo.api.response.ResponseTransaction
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @POST("login")
    fun login(@Body requestBody: RequestBody): Call<ResponseLogin>

    @POST("register")
    fun register(@Body requestBody: RequestBody): Call<ResponseRegister>

    @GET("api/recycled-goods")
    fun goods(@Header("Authorization") token: String): Call<ResponseGoods>

    @GET("api/recycled-items")
    fun getProducts(@Header("Authorization") token: String): Call<ResponseProducts>

    @GET("api/recycled-goods")
    suspend fun paggingGoods(@Header("Authorization") token: String, @Query("page") page: Int,
                             @Query("size") size: Int): Response<ResponseGoods>

    @DELETE("api/recycled-goods/{id}")
    fun deleteGoods(@Header("Authorization") token: String, @Path("id") id: String): Call<ResponseDeleteGoods>

    @Multipart
    @POST("api/recycled-goods")
    fun addGoods(
        @Header("Authorization") token: String,
        @Part("title") name : RequestBody,
        @Part("price") price : RequestBody,
        @Part("kind") kind : RequestBody,
        @Part image1: MultipartBody.Part,
        @Part image2: MultipartBody.Part,
        @Part image3: MultipartBody.Part,
        @Part("desc") desc : RequestBody,
        @Part("lat") lat : RequestBody,
        @Part("long") long : RequestBody,
        @Part("amount") amount : RequestBody,
    ): Call<ResponseAdd>
    @Multipart
    @PUT("api/recycled-goods/{id}")
    fun editGoods(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Part("title") name : RequestBody,
        @Part("price") price : RequestBody,
        @Part("kind") kind : RequestBody,
        @Part image1: MultipartBody.Part,
        @Part image2: MultipartBody.Part,
        @Part image3: MultipartBody.Part,
        @Part("desc") desc : RequestBody,
        @Part("lat") lat : RequestBody,
        @Part("long") long : RequestBody,
        @Part("amount") amount : RequestBody,
    ): Call<ResponseAdd>

    @GET("api/wastes")
    fun wastes(@Header("Authorization") token: String): Call<ResponseGoods>

    @DELETE("api/wastes/{id}")
    fun deletewaste(@Header("Authorization") token: String, @Path("id") id: String): Call<ResponseDeleteGoods>

    @Multipart
    @POST("api/wastes")
    fun addWaste(
        @Header("Authorization") token: String,
        @Part("title") name : RequestBody,
        @Part("price") price : RequestBody,
        @Part("kind") kind : RequestBody,
        @Part image1: MultipartBody.Part,
        @Part image2: MultipartBody.Part,
        @Part image3: MultipartBody.Part,
        @Part("desc") desc : RequestBody,
        @Part("lat") lat : RequestBody,
        @Part("long") long : RequestBody,
        @Part("amount") amount : RequestBody,
    ): Call<ResponseAdd>
    @Multipart
    @PUT("api/wastes/{id}")
    fun editWaste(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Part("title") name : RequestBody,
        @Part("price") price : RequestBody,
        @Part("kind") kind : RequestBody,
        @Part image1: MultipartBody.Part,
        @Part image2: MultipartBody.Part,
        @Part image3: MultipartBody.Part,
        @Part("desc") desc : RequestBody,
        @Part("lat") lat : RequestBody,
        @Part("long") long : RequestBody,
        @Part("amount") amount : RequestBody,
    ): Call<ResponseAdd>

    @POST("api/item-carts")
    fun addToCart(@Header("Authorization") token: String,@Body requestBody: RequestBody): Call<ResponseAddToCart>

    @GET("api/item-carts")
    fun getAllCarts(@Header("Authorization") token: String): Call<ResponseCarts>

    @PUT("api/item-carts/{id}?operation=plus")
    fun plusCart(@Header("Authorization") token: String,@Path("id") id :String): Call<ResponseOpCart>

    @PUT("api/item-carts/{id}?operation=min")
    fun minCart(@Header("Authorization") token: String,@Path("id") id :String): Call<ResponseOpCart>

    @DELETE("api/item-carts/{id}")
    fun deleteCart(@Header("Authorization") token: String,@Path("id") id :String): Call<ResponseDeleteCart>

    @GET("api/transactions")
    fun getAllTransactions(@Header("Authorization") token: String): Call<ResponseTransaction>

    @POST("api/transactions")
    fun makeTransaction(@Header("Authorization") token: String): Call<ResponseAddTransaction>

    @Multipart
    @POST("api/recommendation-price")
    fun predictPrice(@Header("Authorization") token: String,@Part image: MultipartBody.Part): Call<ResponsePrediction>
}
