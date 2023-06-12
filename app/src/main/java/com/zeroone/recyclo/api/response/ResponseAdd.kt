package com.zeroone.recyclo.api.response

import com.google.gson.annotations.SerializedName

data class ResponseAdd(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("amount")
	val amount: String,

	@field:SerializedName("image3")
	val image3: String,

	@field:SerializedName("price")
	val price: String,

	@field:SerializedName("kind")
	val kind: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("image1")
	val image1: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("image2")
	val image2: String,

	@field:SerializedName("lat")
	val lat: String,

	@field:SerializedName("long")
	val jsonMemberLong: String,

	@field:SerializedName("desc")
	val desc: String
)
