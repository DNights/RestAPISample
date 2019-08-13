package com.dnights.restfullapisampletest.activity

import android.os.Bundle
import com.dnights.restfullapisampletest.adapter.RecyclerAdapter
import com.dnights.restfullapisampletest.api.API
import com.dnights.restfullapisampletest.api.Urls
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.dnights.restfullapisampletest.R
import com.dnights.restfullapisampletest.api.AccessKey
import com.dnights.restfullapisampletest.api.RetrofitAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initLayout()
        callAPIFetchPhotos()
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
                (recycler_image_list.adapter as RecyclerAdapter).setList(it)
                recycler_image_list.adapter!!.notifyDataSetChanged()
            },{
                it.printStackTrace()
            }).let {
                addDisposable(it)
            }
    }


}
