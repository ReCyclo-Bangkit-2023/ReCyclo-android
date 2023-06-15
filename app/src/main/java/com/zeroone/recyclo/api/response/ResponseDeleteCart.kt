package com.zeroone.recyclo.api.response

import com.google.gson.annotations.SerializedName

data class ResponseDeleteCart(

	@field:SerializedName("data")
	val data: DataDeleteCart,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataDeleteCart(
	val any: Any? = null
)
