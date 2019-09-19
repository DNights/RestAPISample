package com.dnights.restfullapisampletest.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.dnights.restfullapisampletest.api.API
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.dnights.restfullapisampletest.R
import com.dnights.restfullapisampletest.api.data.PhotoData
import com.dnights.restfullapisampletest.paging.PhotoDataSource
import com.dnights.restfullapisampletest.paging.PhotoPageAdepter
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


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

        val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(10)
            .setPageSize(10)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(true)
            .build()

        val builder = RxPagedListBuilder<Int, PhotoData>(object: DataSource.Factory<Int, PhotoData>() {
            override fun create(): DataSource<Int, PhotoData> {
                return PhotoDataSource(API.create(), compositeDisposable)
            }
        }, config)

        builder.buildObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                Log.d("test" , "$it")
                (recycler_image_list.adapter as PhotoPageAdepter).submitList(it)
        }.let { addDisposable(it) }

        recycler_image_list.adapter = PhotoPageAdepter()
    }

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




}
