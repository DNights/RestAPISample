package com.dnights.restfullapisampletest.paging

import androidx.paging.PageKeyedDataSource
import com.dnights.restfullapisampletest.api.API
import com.dnights.restfullapisampletest.api.AccessKey
import com.dnights.restfullapisampletest.api.data.PhotoData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class PhotoDataSource(private val api: API, private val compositeDisposable: CompositeDisposable) :
    PageKeyedDataSource<Int, PhotoData>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, PhotoData>
    ) {
        api.fetchPhotos(AccessKey.accessKey, 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val nextPage = getNextPage(it)
                val body = it.body() ?: emptyList()
                callback.onResult(body, 0, nextPage)
            }, {
                //TODO "exception loadInitial"
                it.printStackTrace()
            }).let { compositeDisposable.add(it) }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoData>) {

        api.fetchPhotos(AccessKey.accessKey, params.key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val nextPage = getNextPage(it)
                val body = it.body() ?: emptyList()
                callback.onResult(body, nextPage)
            }, {
                //TODO "exception loadAfter"
                it.printStackTrace()
            }).let { compositeDisposable.add(it) }
    }

    /**
     * links structure first
     * <https://api.unsplash.com/photos?client_id=XXX&page=13340>; rel="last",
     * <https://api.unsplash.com/photos?client_id=XXX&page=2>; rel="next"
     *
     * links structure next
     * <https://api.unsplash.com/photos?client_id=XXX&page=1>; rel="first",
     * <https://api.unsplash.com/photos?client_id=XXX&page=27>; rel="prev",
     * <https://api.unsplash.com/photos?client_id=XXX&page=13340>; rel="last",
     * <https://api.unsplash.com/photos?client_id=XXX&page=29>; rel="next"
     */

    private fun getLinkFromHeader(it: Response<List<PhotoData>>): String {
        val links = it.headers().get("link") ?: ""
        require(links.isNotEmpty()) { "lisks is empty" }
        return links
    }

    private fun getNextPage(it: Response<List<PhotoData>>): Int {
        val links = getLinkFromHeader(it).split(",")
        return links[links.lastIndex]
            .replace(" <", "")
            .replace(">; rel=\"next\"", "")
            .split("page=")[1]
            .toInt()
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoData>) {}
}