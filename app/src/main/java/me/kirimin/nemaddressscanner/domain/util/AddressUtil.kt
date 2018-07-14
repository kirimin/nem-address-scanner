package me.kirimin.nemaddressscanner.domain.util

import com.google.gson.Gson
import com.google.gson.JsonObject
import me.kirimin.nemaddressscanner.domain.model.SendAddressJson

object AddressUtil {

    fun isSendJsonFormat(address: String) = address.isNotBlank() && address[0] == '{'

    fun parseJsonToAddress(address: String): String {
        val json = Gson().fromJson(address, SendAddressJson::class.java)
        return json.data?.addr ?: ""
    }
}