package com.example.osrscritic.network

import com.example.osrscritic.model.RunescapeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.File

interface RetrofitService {

    @GET("profile")
    fun getOSRSPlayer(
        @Query("user") user: String
    ): Call<RunescapeResponse>



}