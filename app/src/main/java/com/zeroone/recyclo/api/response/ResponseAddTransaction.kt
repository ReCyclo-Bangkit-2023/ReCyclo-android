package com.zeroone.recyclo.api.response

import com.google.gson.annotations.SerializedName

data class ResponseAddTransaction(

	@field:SerializedName("data")
	val data: DataAddTransaction,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class RecycledItem(

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

data class DataAddTransaction(

	@field:SerializedName("totalAmount")
	val totalAmount: Int,

	@field:SerializedName("orderedTimestamp")
	val orderedTimestamp: Long,

	@field:SerializedName("totalPrice")
	val totalPrice: Int,

	@field:SerializedName("orderedDate")
	val orderedDate: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("recycledItems")
	val recycledItems: List<RecycledItemsItem>,

	@field:SerializedName("statusTransaction")
	val statusTransaction: String
)

data class RecycledItemsItem(

	@field:SerializedName("itemCartAmount")
	val itemCartAmount: Int,

	@field:SerializedName("statusItemTransaction")
	val statusItemTransaction: String,

	@field:SerializedName("recycledItem")
	val recycledItem: RecycledItem,

	@field:SerializedName("message")
	val message: String
)
