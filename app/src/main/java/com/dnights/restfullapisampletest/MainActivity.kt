package com.dnights.restfullapisampletest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dnights.restfullapisampletest.adapter.RecyclerAdapter
import com.dnights.restfullapisampletest.api.API
import com.dnights.restfullapisampletest.api.Urls
import com.dnights.restfullapisampletest.api.data.PhotoData
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.recyclerview.widget.LinearLayoutManager
import com.dnights.restfullapisampletest.api.AccessKey

class MainActivity : AppCompatActivity() {

    var adapter = RecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initLayout()

        getPhotos()
    }

    private fun initLayout(){
        val linearLayoutManager = LinearLayoutManager(this)
        recycler_image_list.layoutManager = linearLayoutManager
        recycler_image_list.adapter = adapter
    }

    private fun getPhotos(){
        val retofit = Retrofit.Builder()
            .baseUrl(Urls.getBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retofit.create(API::class.java)
            .fetchPotos(AccessKey.accessKey)
            .enqueue(object: Callback<List<PhotoData>> {
                override fun onResponse(call: Call<List<PhotoData>>, response: Response<List<PhotoData>>) {
                    if(response.isSuccessful){

                        val body = response.body()

                        requireNotNull(body) { "body is null" }

                        adapter.setList(body)
                        adapter.notifyDataSetChanged()

                    }
                }

                override fun onFailure(call: Call<List<PhotoData>>, t: Throwable) {
                    t.printStackTrace()
                }

            })
    }
}
