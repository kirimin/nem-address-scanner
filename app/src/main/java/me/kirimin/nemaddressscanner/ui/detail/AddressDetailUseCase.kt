package me.kirimin.nemaddressscanner.ui.detail

import io.reactivex.Single
import me.kirimin.nemaddressscanner.data.entity.AccountEntity
import me.kirimin.nemaddressscanner.data.entity.MosaicEntity
import me.kirimin.nemaddressscanner.data.entity.MosaicOwnedResponse
import me.kirimin.nemaddressscanner.data.repository.NisRepository

class AddressDetailUseCase {

    val nisRepository = NisRepository()

    fun requestAccount(address: String): Single<AccountEntity> {
        return nisRepository.getAccount(address).map { it.account }
    }

    fun requestMosaics(address: String): Single<List<MosaicEntity>> {
        return nisRepository.getMosaics(address).map { it.data }
    }
}