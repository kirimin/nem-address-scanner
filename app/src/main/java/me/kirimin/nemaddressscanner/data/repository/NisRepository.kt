package me.kirimin.nemaddressscanner.data.repository

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.kirimin.nemaddressscanner.data.network.NisService
import me.kirimin.nemaddressscanner.data.entity.AccountResponse
import me.kirimin.nemaddressscanner.data.entity.MosaicOwnedResponse
import me.kirimin.nemaddressscanner.data.network.RetrofitClient

class NisRepository {

    fun getAccount(address: String): Single<AccountResponse> {
        return RetrofitClient.default().build().create(NisService::class.java).getAccount(address)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getMosaics(address: String): Single<MosaicOwnedResponse> {
        return RetrofitClient.default().build().create(NisService::class.java).getMosaics(address)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }
}