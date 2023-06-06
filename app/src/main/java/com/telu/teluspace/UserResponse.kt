package com.telu.teluspace

import com.google.gson.annotations.SerializedName

data class UserResponse(
	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("message")
	val message: String
)
