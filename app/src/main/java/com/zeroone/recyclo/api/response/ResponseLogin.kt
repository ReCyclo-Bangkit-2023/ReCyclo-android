package com.zeroone.recyclo.api.response

import com.google.gson.annotations.SerializedName

data class ResponseLogin(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("token")
	val token: String
)
