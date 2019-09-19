package com.dnights.restfullapisampletest.activity

import android.os.Bundle
import android.util.Log
import com.dnights.restfullapisampletest.adapter.RecyclerAdapter
import com.dnights.restfullapisampletest.api.API
import com.dnights.restfullapisampletest.api.Urls
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.dnights.restfullapisampletest.R
import com.dnights.restfullapisampletest.api.AccessKey
import com.dnights.restfullapisampletest.api.RetrofitAdapter
import com.dnights.restfullapisampletest.api.data.PhotoData
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.reflect.Type


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initLayout()
        //callAPIFetchPhotos()
    }

    private fun initLayout(){
        val linearLayoutManager = LinearLayoutManager(this)
        recycler_image_list.layoutManager = linearLayoutManager
        recycler_image_list.adapter = RecyclerAdapter()
    }

    private fun callAPIFetchPhotos(){
        RetrofitAdapter.getInstance(Urls.getBaseUrl())
            .create(API::class.java)
            .fetchPhotos(AccessKey.accessKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                val links = it.headers().get("link")?:""

                require(links.isNotEmpty()){ "Header lisk is empty" }

                val lastLink = links.split(",")[0].replace("<","").replace(">; rel=\"last\"", "")
                val nextLink = links.split(",")[1].replace(" <","").replace(">; rel=\"next\"","")

                Log.d("test", "links = $links")
                Log.d("test", "lastLink = $lastLink")
                Log.d("test", "nextLink = $nextLink")

//    private fun callAPIFetchPhotos(){
//        RetrofitAdapter.getInstance(Urls.getBaseUrl())
//            .create(API::class.java)
//            .fetchPhotos(AccessKey.accessKey)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//
//                val links = it.headers().get("link")?:""
//
//                require(links.isNotEmpty()){ "Header lisk is empty" }
//
//                val lastLink = links.split(",")[0].replace("<","").replace(">; rel=\"last\"", "")
//                val nextLink = links.split(",")[1].replace(" <","").replace(">; rel=\"next\"","")
//
//                Log.d("test", "links = $links")
//                Log.d("test", "lastLink = $lastLink")
//                Log.d("test", "nextLink = $nextLink")
//
//
//                Log.d("test", "response = ${it.body()}")
//
//                (recycler_image_list.adapter as RecyclerAdapter).setList(it.body()?: emptyList())
//                recycler_image_list.adapter!!.notifyDataSetChanged()
//            },{
//                it.printStackTrace()
//            }).let {
//                addDisposable(it)
//            }
//    }

                Log.d("test", "response = ${it.body()}")

                (recycler_image_list.adapter as RecyclerAdapter).setList(it.body()?: emptyList())
                recycler_image_list.adapter!!.notifyDataSetChanged()
            },{
                it.printStackTrace()
            }).let {
                addDisposable(it)
            }
    }


}
