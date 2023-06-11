package com.zeroone.recyclo.api.response

import com.google.gson.annotations.SerializedName

data class ResponseDeleteGoods(

	@field:SerializedName("data")
	val data: DataDelete,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataDelete(
	val any: Any? = null
)
