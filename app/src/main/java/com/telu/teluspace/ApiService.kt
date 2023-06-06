package com.telu.teluspace

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("api/gedung")
    fun getGedung(): Call<GedungResponse>
    @GET("api/ruangan/{id}")
    fun getRuanganByGedung(
        @Path("id") id: Int
    ): Call<RuanganByGedungResponse>

    @FormUrlEncoded
    @POST("api/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<UserResponse>
}