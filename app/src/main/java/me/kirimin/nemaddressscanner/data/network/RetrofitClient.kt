package me.kirimin.nemaddressscanner.data.network

import me.kirimin.nemaddressscanner.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val endPoint = "http://176.9.68.110:7890/"
    private val defaultClient: OkHttpClient

    init {
        val httpClient = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
        }
        defaultClient = httpClient.build()
    }

    fun default(): Retrofit.Builder {
        return Retrofit.Builder()
                .baseUrl(endPoint)
                .client(defaultClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }
}