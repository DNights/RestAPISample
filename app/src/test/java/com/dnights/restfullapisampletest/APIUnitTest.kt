package com.dnights.restfullapisampletest

import com.dnights.restfullapisampletest.api.API
import com.dnights.restfullapisampletest.api.AccessKey
import com.dnights.restfullapisampletest.api.RetrofitAdapter
import com.dnights.restfullapisampletest.api.Urls
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Test

class APIUnitTest {
    val compositeDisposable = CompositeDisposable()

    @Test
    fun apiTest() {
        RetrofitAdapter.getInstance(Urls.getBaseUrl())
            .create(API::class.java)
            .fetchPhotos(AccessKey.accessKey, 1)
            .subscribeOn(TrampolineSchedulerProvider().io())
            .observeOn(TrampolineSchedulerProvider().ui())
            .subscribe({response ->
                println(response.headers())
                val list = response.body()?: emptyList()
                list.map {data ->
                    println(data.toString())
                }
            },{
                it.printStackTrace()
            }).let {
                compositeDisposable.add(it)
            }
    }

    @After
    fun after() {
        compositeDisposable.clear()
    }
}

interface BaseSchedulerProvider {
    fun computation(): Scheduler
    fun io(): Scheduler
    fun ui(): Scheduler
}

class TrampolineSchedulerProvider : BaseSchedulerProvider {
    override fun computation(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun io(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun ui(): Scheduler {
        return Schedulers.trampoline()
    }
}