package com.zeroone.recyclo.api.response

import com.google.gson.annotations.SerializedName

data class ResponseAddToCart(

	@field:SerializedName("data")
	val data: DataCart,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataCart(

	@field:SerializedName("id")
	val id: String
)
