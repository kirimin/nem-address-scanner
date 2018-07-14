package me.kirimin.nemaddressscanner.ui.detail

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.text.TextUtils
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import me.kirimin.nemaddressscanner.data.entity.AccountEntity
import me.kirimin.nemaddressscanner.data.entity.MosaicEntity
import me.kirimin.nemaddressscanner.domain.util.ZxingUtil
import android.view.View
import me.kirimin.nemaddressscanner.domain.util.AddressUtil


class AddressDetailViewModel {

    private var view: AddressDetailView? = null
    private lateinit var useCase: AddressDetailUseCase

    private lateinit var account: AccountEntity
    private lateinit var disposables: CompositeDisposable

    var addressField = ObservableField<String>()
    var balanceField = ObservableField<String>()
    var mosaicsField = ObservableField<String>()
    var isLoadingField = ObservableBoolean(false)
    var hasAccountDataField = ObservableBoolean(false)

    fun onCreate(view: AddressDetailView, qrString: String) {
        this.view = view
        useCase = AddressDetailUseCase()
        disposables = CompositeDisposable()

        val address = if (AddressUtil.isSendJsonFormat(qrString)) {
            AddressUtil.parseJsonToAddress(qrString)
        } else {
            qrString
        }
        view.initToolbar(address)
        isLoadingField.set(true)
        useCase.requestAccount(address)
                .zipWith(useCase.requestMosaics(address), BiFunction<AccountEntity, List<MosaicEntity>, Pair<AccountEntity, List<MosaicEntity>>> { t1, t2 ->
                    Pair(t1, t2)
                }).subscribe({ pair ->
                    this.account = pair.first
                    isLoadingField.set(false)
                    hasAccountDataField.set(true)
                    addressField.set(account.address)
                    balanceField.set(account.displayBalance)
                    mosaicsField.set(TextUtils.join("", pair.second.map { "name: ${it.displayName}\nquantity: ${it.quantity}\n\n" }))
                    val qr = ZxingUtil.createQRCodeByZxing(account.address, 300)
                    view.setQrImage(qr)
                }, {
                    it.printStackTrace()
                    isLoadingField.set(false)
                    view.showError()
                    view.finishView()
                }).let { disposables.add(it) }
    }

    fun onDestroy() {
        disposables.clear()
        this.view = null
    }

    fun onClickCopyButton(view: View) {
        this.view?.copyToClipboard(account.address)
    }
}