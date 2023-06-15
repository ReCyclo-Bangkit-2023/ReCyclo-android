package com.zeroone.recyclo.api.response

import com.google.gson.annotations.SerializedName

data class ResponseTransaction(

	@field:SerializedName("data")
	val data: List<DataItemTransactionAll>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class RecycledItem2(

	@field:SerializedName("itemCartAmount")
	val itemCartAmount: Int,

	@field:SerializedName("statusItemTransaction")
	val statusItemTransaction: String,

	@field:SerializedName("recycledItem")
	val recycledItem: RecycledItem3,

	@field:SerializedName("message")
	val message: String
)

data class DataItemTransactionAll(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("amount")
	val amount: Int,

	@field:SerializedName("orderedTimestamp")
	val orderedTimestamp: Long,

	@field:SerializedName("totalPrice")
	val totalPrice: Int,

	@field:SerializedName("recycledId")
	val recycledId: String,

	@field:SerializedName("orderedDate")
	val orderedDate: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("key")
	val key: Int,

	@field:SerializedName("statusTransaction")
	val statusTransaction: String,

	@field:SerializedName("totalAmount")
	val totalAmount: Int,

	@field:SerializedName("recycledItems")
	val recycledItems: List<RecycledItem2>
)

data class RecycledItem3(

	@field:SerializedName("sold")
	val sold: Int,

	@field:SerializedName("image3")
	val image3: String,

	@field:SerializedName("amount")
	val amount: Int,

	@field:SerializedName("recycledType")
	val recycledType: String,

	@field:SerializedName("kind")
	val kind: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("image1")
	val image1: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("image2")
	val image2: String,

	@field:SerializedName("long")
	val jsonMemberLong: String,

	@field:SerializedName("price")
	val price: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("lat")
	val lat: String,

	@field:SerializedName("desc")
	val desc: String
)
