package com.telu.teluspace

import com.google.gson.annotations.SerializedName

data class RuanganByGedungResponse(

	@field:SerializedName("data")
	val data: ArrayList<RuanganItem>,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class RuanganItem(

	@field:SerializedName("status_ruangan")
	val statusRuangan: String,

	@field:SerializedName("id_gedung")
	val idGedung: String,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("nomor_ruangan")
	val nomorRuangan: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int
)
