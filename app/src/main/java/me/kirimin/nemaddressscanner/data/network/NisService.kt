package me.kirimin.nemaddressscanner.data.network

import io.reactivex.Single
import me.kirimin.nemaddressscanner.data.entity.AccountResponse
import me.kirimin.nemaddressscanner.data.entity.MosaicOwnedResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NisService {

    @GET("account/get")
    fun getAccount(@Query("address") address: String): Single<AccountResponse>

    @GET("/account/mosaic/owned")
    fun getMosaics(@Query("address") address: String): Single<MosaicOwnedResponse>
}