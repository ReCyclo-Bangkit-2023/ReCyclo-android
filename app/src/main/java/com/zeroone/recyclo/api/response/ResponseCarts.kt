package com.zeroone.recyclo.api.response

import com.google.gson.annotations.SerializedName

data class ResponseCarts(

	@field:SerializedName("data")
	val data: List<DataItemCart>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataItemCart(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("amount")
	val amount: Int,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("price")
	val price: String,

	@field:SerializedName("recycledId")
	val recycledId: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("key")
	val key: Int
)
