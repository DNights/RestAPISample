package com.dnights.restfullapisampletest.paging

import androidx.paging.PageKeyedDataSource
import com.dnights.restfullapisampletest.api.API
import com.dnights.restfullapisampletest.api.AccessKey
import com.dnights.restfullapisampletest.api.data.PhotoData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PhotoDataSource(private val api:API, private val compositeDisposable: CompositeDisposable) : PageKeyedDataSource<Int, PhotoData>(){

    var nextPage = -1

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, PhotoData>
    ) {
        api.fetchPhotos(AccessKey.accessKey, 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val links = it.headers().get("link")?:""

                require(links.isNotEmpty()) { "lisks is empty" }

                nextPage = links.split(",")[1]
                    .replace(" <","")
                    .replace(">; rel=\"next\"","")
                    .split("page=")[1]
                    .toInt()

                val body = it.body()?: emptyList()
                callback.onResult(body, 1, nextPage)
            },{
                it.printStackTrace()
            }).let { compositeDisposable.add(it) }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoData>) {
        api.fetchPhotos(AccessKey.accessKey, nextPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val links = it.headers().get("link")?:""

                require(links.isNotEmpty()) { "lisks is empty" }

                nextPage = links
                    .split(",")[3]
                    .replace(" <","")
                    .replace(">; rel=\"next\"","")
                    .split("page=")[1]
                    .toInt()

                callback.onResult(it.body()?: emptyList(),  nextPage)
            },{
                it.printStackTrace()
            }).let { compositeDisposable.add(it) }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoData>) {

    }

}