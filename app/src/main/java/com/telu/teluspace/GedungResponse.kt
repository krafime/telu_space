package com.telu.teluspace

import com.google.gson.annotations.SerializedName

data class GedungResponse(

	@field:SerializedName("data")
	val data: ArrayList<GedungItem>,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class GedungItem(

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("status_gedung")
	val statusGedung: String,

	@field:SerializedName("nama_gedung")
	val namaGedung: String,

	@field:SerializedName("alamat")
	val alamat: String
)
