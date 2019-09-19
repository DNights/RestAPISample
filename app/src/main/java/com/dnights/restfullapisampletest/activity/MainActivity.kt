package com.dnights.restfullapisampletest.activity

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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initLayout()
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

}
