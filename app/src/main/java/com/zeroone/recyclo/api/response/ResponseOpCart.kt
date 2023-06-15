package com.zeroone.recyclo.api.response

import com.google.gson.annotations.SerializedName

data class ResponseOpCart(

	@field:SerializedName("data")
	val data: DataOpCart,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataOpCart(

	@field:SerializedName("amount")
	val amount: Int
)
