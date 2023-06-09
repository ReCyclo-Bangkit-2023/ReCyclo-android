package com.zeroone.recyclo.api.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ResponseProducts(

	@field:SerializedName("data")
	val data: List<DataItemproduct>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
) : Parcelable

@Parcelize
data class DataItemproduct(

	@field:SerializedName("sold")
	val sold: Int,

	@field:SerializedName("amount")
	val amount: String,

	@field:SerializedName("image3")
	val image3: String,

	@field:SerializedName("sellerDetails")
	val sellerDetails: SellerDetails,

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
	val lon: String,

	@field:SerializedName("price")
	val price: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("key")
	val key: Int,

	@field:SerializedName("lat")
	val lat: String,

	@field:SerializedName("desc")
	val desc: String
) : Parcelable

@Parcelize
data class SellerDetailsProduct(

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("fullname")
	val fullname: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("email")
	val email: String
) : Parcelable
