package com.zeroone.recyclo.api.response

import com.google.gson.annotations.SerializedName

data class ResponsePrediction(

	@field:SerializedName("data")
	val data: DataPrediction,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataPrediction(

	@field:SerializedName("recommendedPrice")
	val recommendedPrice: Int
)
