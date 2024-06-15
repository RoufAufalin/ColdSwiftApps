package com.bangkit.coldswiftapps.data.remote

import com.bangkit.coldswiftapps.data.remote.response.BuyTiketResponse
import com.bangkit.coldswiftapps.data.remote.response.DetailEventResponse
import com.bangkit.coldswiftapps.data.remote.response.ListEventResponse
import com.bangkit.coldswiftapps.data.remote.response.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @FormUrlEncoded
    @POST("api/users/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>


    @GET("events")
    fun getAllEvent(): Call<List<ListEventResponse>>

    @GET("events/{id}")
    suspend fun getDetailEvent(
        @Path("id") id: String,
    ): DetailEventResponse

    @POST("events/{id}/purchase")
    suspend fun purchaseEvent(
        @Path("id") id: String,
    ): BuyTiketResponse




}