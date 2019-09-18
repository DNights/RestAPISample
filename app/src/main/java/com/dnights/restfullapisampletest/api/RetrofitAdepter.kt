package com.dnights.restfullapisampletest.api

import okhttp3.Interceptor
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.*
import java.util.concurrent.TimeUnit

object RetrofitAdapter {

    private const val TIMEOUT = 40L

    private val _instances = Hashtable<String, Retrofit>()

    @Synchronized
    fun getInstance(baseUrl: String): Retrofit {

        val key = baseUrl

        if(!_instances.containsKey(key)){
            _instances[key] = createInstanceWithUrl(baseUrl = baseUrl)
        }

        return _instances[key]!!
    }

    private fun createInstanceWithUrl(baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(createClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private fun createClient(): OkHttpClient = createHttpClientBuilder().build()

    private fun createHttpClientBuilder(): OkHttpClient.Builder =
        OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .cookieJar(JavaNetCookieJar(cookieManager()))
            .addInterceptor(httpHeaderInterceptor())
            .addInterceptor(httpLoggingInterceptor())

    private fun cookieManager(): CookieManager {
        val cookieManager = CookieManager()
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
        return cookieManager
    }

    private fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        return httpLoggingInterceptor
    }

    private fun httpHeaderInterceptor(): Interceptor =
        Interceptor{
            val request = it.request().newBuilder().addHeader("Accept-Version","v1").build()
            return@Interceptor it.proceed(request)
        }
}