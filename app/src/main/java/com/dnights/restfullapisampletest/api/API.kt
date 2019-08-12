package com.dnights.restfullapisampletest.api

import com.dnights.restfullapisampletest.api.data.PhotoData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface API {
    @GET("/photos/")
    fun fetchPotos(@Query("client_id") id:String):Call<List<PhotoData>>
}