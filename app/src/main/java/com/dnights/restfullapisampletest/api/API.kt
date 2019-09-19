package com.dnights.restfullapisampletest.api

import com.dnights.restfullapisampletest.api.data.PhotoData
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface API {
    @GET("/photos/")
    fun fetchPhotos(@Query("client_id") id:String, @Query("page") page:Int): Single<Response<List<PhotoData>>>

    companion object{
        fun create(): API {
            return RetrofitAdapter.getInstance(Urls.getBaseUrl())
                .create(API::class.java)
        }
    }
}